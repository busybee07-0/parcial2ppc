package com.javierf.planahorroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierf.planahorroapp.data.repository.SavingsRepository
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repository: SavingsRepository = SavingsRepository()
) : ViewModel() {

    fun registerPayment(
        memberId: String,
        planId: String,
        amount: Double,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.createPayment(
                    memberId = memberId,
                    planId = planId,
                    amount = amount
                )

                onSuccess() // Notifica a la UI

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

