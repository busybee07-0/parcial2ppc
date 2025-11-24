package com.javierf.planahorroapp.data.remote.dto

data class CreatePlanRequest(
    val name: String,
    val motive: String,
    val targetAmount: Double,
    val months: Int,
    val members: List<String>
)
