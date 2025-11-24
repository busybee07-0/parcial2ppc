package com.javierf.planahorroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.repository.SavingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreatePlanViewModel(
    private val repository: SavingsRepository = SavingsRepository()
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow<PlanDto?>(null)
    val success: StateFlow<PlanDto?> = _success


    /**
     * CREA EL PLAN Y LUEGO CREA LOS MIEMBROS UNO POR UNO
     */
    fun createPlan(
        name: String,
        motive: String,
        targetAmount: Double,
        months: Int,
        members: List<String>
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                // 1️⃣ Primero creamos el plan (ENVIANDO ALSO members = listOf() )
                val createdPlan = repository.createPlan(
                    name = name,
                    motive = motive,
                    targetAmount = targetAmount,
                    months = months,
                    members = emptyList()   // ← importante (el backend lo requiere)
                )

                // 2️⃣ Crear cada miembro
                members.forEach { memberName ->
                    repository.createMember(
                        memberName = memberName,
                        planId = createdPlan._id
                    )
                }

                // 3️⃣ Éxito
                _success.value = createdPlan

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
