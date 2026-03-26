package com.potaninpm.network

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.network.dadata.DadataApi
import com.potaninpm.network.dadata.interceptor.DadataInterceptor
import com.potaninpm.network.rates.UnirateApi
import com.potaninpm.network.supabase.SupabaseAuthInterceptor
import com.potaninpm.network.supabase.SupabaseAuthenticator
import com.potaninpm.network.supabase.TokenProvider
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.loans.LoansSupabaseApi
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkProvider {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun dadataOkHttpClient(token: String) = OkHttpClient.Builder()
        .addInterceptor(DadataInterceptor(token))
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun supabaseOkHttpClient(sessionManager: SessionManager, tokenProvider: TokenProvider) = OkHttpClient.Builder()
        .addInterceptor(SupabaseAuthInterceptor(tokenProvider))
        .authenticator(SupabaseAuthenticator(sessionManager))
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun provideDadataApi(token: String): DadataApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.DADATA_URL)
            .client(dadataOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DadataApi::class.java)
    }

    fun provideUnirateApi(): UnirateApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.UNIRATE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnirateApi::class.java)
    }

    fun provideApplicationsSupabaseApi(tokenProvider: TokenProvider, sessionManager: SessionManager): ApplicationsSupabaseApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SUPABASE_URL)
            .client(supabaseOkHttpClient(sessionManager, tokenProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApplicationsSupabaseApi::class.java)
    }

    fun provideLoansSupabaseApi(tokenProvider: TokenProvider, sessionManager: SessionManager): LoansSupabaseApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SUPABASE_URL)
            .client(supabaseOkHttpClient(sessionManager, tokenProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoansSupabaseApi::class.java)
    }

    fun provideProfileSupabaseApi(tokenProvider: TokenProvider, sessionManager: SessionManager): ProfileSupabaseApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SUPABASE_URL)
            .client(supabaseOkHttpClient(sessionManager, tokenProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileSupabaseApi::class.java)
    }

    fun provideAuthSupabaseApi(tokenProvider: TokenProvider, sessionManager: SessionManager): AuthSupabaseApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SUPABASE_URL)
            .client(supabaseOkHttpClient(sessionManager, tokenProvider))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthSupabaseApi::class.java)
    }
}
