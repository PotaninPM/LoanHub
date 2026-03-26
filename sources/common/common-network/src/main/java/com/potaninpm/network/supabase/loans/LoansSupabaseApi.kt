package com.potaninpm.network.supabase.loans

import com.potaninpm.network.supabase.loans.models.LoanDetailsDto
import com.potaninpm.network.supabase.loans.models.LoanProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LoansSupabaseApi {

    @GET("/rest/v1/credit_details")
    suspend fun getLoanDetails(
        @Query("product_id") productId: String = "eq.ID",
        @Query("select") select: String = "*"
    ): List<LoanDetailsDto>

    @GET("/rest/v1/loan_products")
    suspend fun getAllProducts(
        @Query("select") select: String = "*",
        @Query("order") order: String = "sort_order.asc"
    ): List<LoanProductDto>

    @GET("/rest/v1/popular_products")
    suspend fun getPopularOffers(
        @Query("select") select: String = "*",
        @Query("order") order: String = "sort_order.asc"
    ): List<LoanProductDto>
}