package com.potaninpm.network.supabase

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.network.ApiConstants
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.auth.models.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SupabaseAuthenticator(
    private val sessionManager: SessionManager
) : Authenticator {

    private val refreshApi: AuthSupabaseApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("apikey", ApiConstants.SUPABASE_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(ApiConstants.SUPABASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthSupabaseApi::class.java)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val currentToken = sessionManager.accessToken
            
            val requestHeader = response.request.header("Authorization")
            if (requestHeader != null && currentToken != null && requestHeader != "Bearer $currentToken") {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            val refreshToken = sessionManager.refreshToken ?: return null

            val refreshResponse = runBlocking {
                runCatching {
                    refreshApi.refreshToken(request = RefreshRequest(refreshToken))
                }.getOrNull()
            }

            if (refreshResponse != null && refreshResponse.isSuccessful) {
                val body = refreshResponse.body()
                if (body != null) {
                    sessionManager.saveSession(
                        access = body.accessToken,
                        refresh = body.refreshToken
                    )
                    
                    return response.request.newBuilder()
                        .header("Authorization", "Bearer ${body.accessToken}")
                        .build()
                }
            }
            
            sessionManager.clear()
            return null
        }
    }
}
