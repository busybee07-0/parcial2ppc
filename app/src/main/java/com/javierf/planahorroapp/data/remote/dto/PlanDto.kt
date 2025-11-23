package com.javierf.planahorroapp.data.remote.dto

data class PlanDto(
    val _id: String,
    val name: String,
    val motive: String?,
    val targetAmount: Double,
    val months: Int,
    val createdAt: String
)
