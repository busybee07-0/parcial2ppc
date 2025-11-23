package com.javierf.planahorroapp.data.remote.dto

data class MemberDto(
    val _id: String,
    val name: String,
    val planId: String,
    val contributionPerMonth: Double,
    val joinedAt: String
)
