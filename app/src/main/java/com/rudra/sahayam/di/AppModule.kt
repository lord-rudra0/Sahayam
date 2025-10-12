package com.rudra.sahayam.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.rudra.sahayam.BuildConfig
import com.rudra.sahayam.data.api.ApiService
import com.rudra.sahayam.data.api.TokenAuthenticator
import com.rudra.sahayam.data.api.TokenInterceptor
import com.rudra.sahayam.data.db.AppDatabase
import com.rudra.sahayam.data.local.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(sessionManager: SessionManager): TokenInterceptor {
        return TokenInterceptor(sessionManager)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(sessionManager: SessionManager, authRepository: dagger.Lazy<com.rudra.sahayam.domain.repository.AuthRepository>): TokenAuthenticator {
        return TokenAuthenticator(sessionManager, authRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = 
        Room.databaseBuilder(context, AppDatabase::class.java, "sahayam.db").build()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application) = 
        LocationServices.getFusedLocationProviderClient(app)

    @Provides
    @Singleton
    fun provideGeocoder(app: Application): Geocoder = 
        Geocoder(app, Locale.getDefault())
}
