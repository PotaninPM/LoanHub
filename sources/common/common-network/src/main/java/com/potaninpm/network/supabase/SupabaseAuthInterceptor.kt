package com.potaninpm.network.supabase

import com.potaninpm.network.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class SupabaseAuthInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("apikey", ApiConstants.SUPABASE_KEY)
            .addHeader("Authorization", "Bearer ${tokenProvider.getToken()}")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}
