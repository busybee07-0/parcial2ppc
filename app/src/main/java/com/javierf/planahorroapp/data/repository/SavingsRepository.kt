package com.javierf.planahorroapp.data.repository

import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.remote.dto.PaymentDto
import com.javierf.planahorroapp.data.remote.dto.MemberDto
import com.javierf.planahorroapp.data.remote.api.CreatePaymentRequest
import com.javierf.planahorroapp.data.remote.api.RetrofitClient



class SavingsRepository {

    private val api = RetrofitClient.api

    // Obtener lista de planes
    suspend fun getPlans(): List<PlanDto> {
        return api.getPlans()
    }

    // Obtener detalle de un plan
    suspend fun getPlanById(id: String): PlanDto {
        return api.getPlanById(id)
    }

    // Obtener miembros de un plan
    suspend fun getMembersByPlan(planId: String): List<MemberDto> {
        return api.getMembersByPlan(planId)
    }

    // Obtener pagos de un plan
    suspend fun getPaymentsByPlan(planId: String): List<PaymentDto> {
        return api.getPaymentsByPlan(planId)
    }

    // Registrar un pago
    suspend fun createPayment(
        memberId: String,
        planId: String,
        amount: Double
    ): PaymentDto {
        val request = CreatePaymentRequest(memberId, planId, amount)
        return api.createPayment(request)
    }
}
