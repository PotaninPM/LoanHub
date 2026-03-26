package com.potaninpm.common.impl

import android.content.Context
import androidx.room.Room
import com.potaninpm.core.auth.SessionManager
import com.potaninpm.database.data.repository.LoanDraftsRepositoryImpl
import com.potaninpm.database.data.room.AppDatabase
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.ApiConstants
import com.potaninpm.network.NetworkProvider
import com.potaninpm.network.dadata.DadataApi
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.network.repository.CurrencyRepositoryImpl
import com.potaninpm.network.supabase.TokenProvider
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.loans.LoansSupabaseApi
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider

class AppComponentImpl(private val context: Context) : AppComponent {

    override val sessionManager by lazy {
        SessionManager(context.getSharedPreferences(SUPABASE_AUTH, Context.MODE_PRIVATE))
    }

    override val authTokenProvider: TokenProvider = object : TokenProvider {
        override fun getToken(): String? = sessionManager.accessToken
        override fun getRefreshToken(): String? = sessionManager.refreshToken
        override fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Int) {
            sessionManager.saveSession(accessToken, refreshToken)
        }
        override fun clear() {
            sessionManager.clear()
        }
    }

    override val applicationsSupabaseApi: ApplicationsSupabaseApi by lazy {
        NetworkProvider.provideApplicationsSupabaseApi(authTokenProvider, sessionManager)
    }

    override val unirateApi: com.potaninpm.network.rates.UnirateApi by lazy {
        NetworkProvider.provideUnirateApi()
    }

    override val currencyRepository: CurrencyRepository by lazy {
        CurrencyRepositoryImpl(unirateApi)
    }

    override val loansSupabaseApi: LoansSupabaseApi by lazy {
        NetworkProvider.provideLoansSupabaseApi(authTokenProvider, sessionManager)
    }

    override val authSupabaseApi: AuthSupabaseApi by lazy {
        NetworkProvider.provideAuthSupabaseApi(authTokenProvider, sessionManager)
    }

    override val profileSupabaseApi: ProfileSupabaseApi by lazy {
        NetworkProvider.provideProfileSupabaseApi(authTokenProvider, sessionManager)
    }

    override val dadataApi: DadataApi by lazy {
        NetworkProvider.provideDadataApi(ApiConstants.DADATA_API_KEY)
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE
        ).build()
    }

    override val loanDraftsRepository: LoanDraftsRepository by lazy {
        LoanDraftsRepositoryImpl(appDatabase.loanDao())
    }
    
    override val dispatcherProvider: DispatcherProvider by lazy {
        DefaultDispatcherProvider()
    }

    private companion object {
        const val SUPABASE_AUTH = "supabase_auth"
        const val APP_DATABASE = "loans.db"
    }
}
