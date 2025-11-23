package com.javierf.planahorroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.javierf.planahorroapp.data.repository.SavingsRepository
import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.remote.dto.PaymentDto
import com.javierf.planahorroapp.data.remote.dto.MemberDto

class PlanDetailViewModel(
    private val repository: SavingsRepository = SavingsRepository()
) : ViewModel() {

    private val _plan = MutableStateFlow<PlanDto?>(null)
    val plan: StateFlow<PlanDto?> = _plan

    private val _members = MutableStateFlow<List<MemberDto>>(emptyList())
    val members: StateFlow<List<MemberDto>> = _members

    private val _payments = MutableStateFlow<List<PaymentDto>>(emptyList())
    val payments: StateFlow<List<PaymentDto>> = _payments

    fun loadPlan(planId: String) {
        viewModelScope.launch {
            try {
                _plan.value = repository.getPlanById(planId)
                _members.value = repository.getMembersByPlan(planId)
                _payments.value = repository.getPaymentsByPlan(planId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
