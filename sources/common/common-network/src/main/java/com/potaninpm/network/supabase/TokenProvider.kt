package com.potaninpm.network.supabase

interface TokenProvider {
    fun getToken(): String?
    fun getRefreshToken(): String?
    fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Int)
    fun clear()
}
