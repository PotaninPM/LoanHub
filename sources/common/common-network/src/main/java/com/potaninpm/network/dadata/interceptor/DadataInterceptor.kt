package com.potaninpm.network.dadata.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class DadataInterceptor(
    private val token: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("Authorization", "Token $token")
            .build()
        return chain.proceed(request)
    }
}