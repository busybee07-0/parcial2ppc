package com.javierf.planahorroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.javierf.planahorroapp.data.repository.SavingsRepository
import com.javierf.planahorroapp.data.remote.dto.PlanDto

class PlansViewModel(
    private val repository: SavingsRepository = SavingsRepository()
) : ViewModel() {

    private val _plans = MutableStateFlow<List<PlanDto>>(emptyList())
    val plans: StateFlow<List<PlanDto>> = _plans

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadPlans() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = repository.getPlans()
                _plans.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _isLoading.value = false
        }
    }
}
