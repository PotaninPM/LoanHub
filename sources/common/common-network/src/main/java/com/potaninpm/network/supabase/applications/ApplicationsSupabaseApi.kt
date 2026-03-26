package com.potaninpm.network.supabase.applications

import com.potaninpm.network.supabase.applications.models.LoanApplicationCreateDto
import com.potaninpm.network.supabase.applications.models.LoanApplicationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApplicationsSupabaseApi {

    @GET("/rest/v1/loan_applications")
    suspend fun getApplications(
        @Query("status") status: String? = null,
        @Query("select") select: String = "*"
    ): List<LoanApplicationDto>

    @GET("/rest/v1/loan_applications")
    suspend fun getApplicationById(
        @Query("id") id: String,
        @Query("select") select: String = "*"
    ): List<LoanApplicationDto>

    @POST("/rest/v1/loan_applications")
    suspend fun createApplication(
        @Body body: LoanApplicationCreateDto
    )

    @PATCH("/rest/v1/loan_applications")
    suspend fun updateApplication(
        @Query("id") id: String,
        @Body body: LoanApplicationDto
    )

    @DELETE("/rest/v1/loan_applications")
    suspend fun deleteApplication(
        @Query("id") id: String
    ): Response<Unit>
}