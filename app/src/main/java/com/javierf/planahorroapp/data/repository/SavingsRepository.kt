package com.javierf.planahorroapp.data.repository

import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.remote.dto.PaymentDto
import com.javierf.planahorroapp.data.remote.dto.MemberDto
import com.javierf.planahorroapp.data.remote.api.CreatePaymentRequest
import com.javierf.planahorroapp.data.remote.api.RetrofitClient
import com.javierf.planahorroapp.data.remote.dto.CreatePlanRequest
import com.javierf.planahorroapp.data.remote.dto.CreateMemberRequest
class SavingsRepository {

    private val api = RetrofitClient.api

    // ---------------------------------------------------------
    // PLANES
    // ---------------------------------------------------------

    suspend fun getPlans(): List<PlanDto> {
        return api.getPlans()
    }

    suspend fun getPlanById(id: String): PlanDto {
        return api.getPlanById(id)
    }

    suspend fun createPlan(
        name: String,
        motive: String,
        targetAmount: Double,
        months: Int,
        members: List<String>   // 👈 AÑADIDO
    ): PlanDto {
        val request = CreatePlanRequest(
            name = name,
            motive = motive,
            targetAmount = targetAmount,
            months = months,
            members = members          // 👈 AÑADIDO
        )
        return api.createPlan(request)
    }

    // ---------------------------------------------------------
    // MIEMBROS
    // ---------------------------------------------------------

    suspend fun getMembersByPlan(planId: String): List<MemberDto> {
        return api.getMembersByPlan(planId)
    }

    suspend fun createMember(
        memberName: String,
        planId: String
    ): MemberDto {
        val req = CreateMemberRequest(
            name = memberName,
            planId = planId,
            contributionPerMonth = 0.0
        )
        return api.createMember(req)
    }

    // ---------------------------------------------------------
    // PAGOS
    // ---------------------------------------------------------

    suspend fun getPaymentsByPlan(planId: String): List<PaymentDto> {
        return api.getPaymentsByPlan(planId)
    }

    suspend fun createPayment(
        memberId: String,
        planId: String,
        amount: Double
    ): PaymentDto {
        val request = CreatePaymentRequest(
            memberId = memberId,
            planId = planId,
            amount = amount
        )
        return api.createPayment(request)
    }
}
