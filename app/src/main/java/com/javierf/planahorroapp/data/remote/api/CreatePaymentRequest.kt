package com.javierf.planahorroapp.data.remote.api

data class CreatePaymentRequest(
    val memberId: String,
    val planId: String,
    val amount: Double
    // el backend pone la fecha automáticamente si no se envía
)
