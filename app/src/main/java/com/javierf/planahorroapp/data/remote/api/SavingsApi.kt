package com.javierf.planahorroapp.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import com.javierf.planahorroapp.data.remote.dto.PlanDto
import com.javierf.planahorroapp.data.remote.dto.PaymentDto
import com.javierf.planahorroapp.data.remote.dto.MemberDto
import com.javierf.planahorroapp.data.remote.dto.CreatePlanRequest
import com.javierf.planahorroapp.data.remote.dto.CreateMemberRequest


interface SavingsApi {

    // ------------------------------------------------------
    // PLANES
    // ------------------------------------------------------

    @GET("api/plans")
    suspend fun getPlans(): List<PlanDto>

    @GET("api/plans/{id}")
    suspend fun getPlanById(
        @Path("id") id: String
    ): PlanDto

    @POST("api/plans")
    suspend fun createPlan(
        @Body request: CreatePlanRequest
    ): PlanDto


    // ------------------------------------------------------
    // MIEMBROS
    // ------------------------------------------------------

    @GET("api/members/plan/{planId}")
    suspend fun getMembersByPlan(
        @Path("planId") planId: String
    ): List<MemberDto>

    @POST("api/members")
    suspend fun createMember(
        @Body request: CreateMemberRequest
    ): MemberDto


    // ------------------------------------------------------
    // PAGOS
    // ------------------------------------------------------

    @GET("api/payments/plan/{planId}")
    suspend fun getPaymentsByPlan(
        @Path("planId") planId: String
    ): List<PaymentDto>

    @POST("api/payments")
    suspend fun createPayment(
        @Body paymentRequest: CreatePaymentRequest
    ): PaymentDto
}
