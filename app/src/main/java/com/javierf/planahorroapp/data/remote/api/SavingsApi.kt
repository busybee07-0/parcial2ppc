package com.javierf.planahorroapp.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.remote.dto.PaymentDto
import com.javierf.planahorroapp.data.remote.dto.MemberDto

interface SavingsApi {

    // Lista de planes
    @GET("api/plans")
    suspend fun getPlans(): List<PlanDto>

    // Detalle de un plan
    @GET("api/plans/{id}")
    suspend fun getPlanById(
        @Path("id") id: String
    ): PlanDto

    // Miembros de un plan
    @GET("api/members/plan/{planId}")
    suspend fun getMembersByPlan(
        @Path("planId") planId: String
    ): List<MemberDto>

    // Pagos de un plan
    @GET("api/payments/plan/{planId}")
    suspend fun getPaymentsByPlan(
        @Path("planId") planId: String
    ): List<PaymentDto>

    // Registrar pago
    @POST("api/payments")
    suspend fun createPayment(
        @Body paymentRequest: CreatePaymentRequest
    ): PaymentDto
}
