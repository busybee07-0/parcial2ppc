package com.javierf.planahorroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.javierf.planahorroapp.data.repository.SavingsRepository

class PaymentViewModel(
    private val repository: SavingsRepository = SavingsRepository()
) : ViewModel() {

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun registerPayment(memberId: String, planId: String, amount: Double) {
        viewModelScope.launch {
            try {
                repository.createPayment(memberId, planId, amount)
                _success.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _success.value = false
            }
        }
    }
}
