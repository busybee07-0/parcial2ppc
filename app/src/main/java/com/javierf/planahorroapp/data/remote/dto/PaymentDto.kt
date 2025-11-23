package com.javierf.planahorroapp.data.remote.dto

data class PaymentDto(
    val _id: String,
    val memberId: String,
    val planId: String,
    val amount: Double,
    val date: String
)
