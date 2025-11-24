package com.javierf.planahorroapp.data.remote.dto

data class CreateMemberRequest(
    val name: String,
    val planId: String,
    val contributionPerMonth: Double = 0.0
)
