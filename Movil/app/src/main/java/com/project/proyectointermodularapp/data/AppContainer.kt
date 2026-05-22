package com.project.proyectointermodularapp.data

import com.project.proyectointermodularapp.BuildConfig
import com.project.proyectointermodularapp.data.network.RequestructureApiService
import com.project.proyectointermodularapp.data.repository.FakeRequestRepository
import com.project.proyectointermodularapp.data.repository.NetworkRequestRepository
import com.project.proyectointermodularapp.data.repository.RequestRepository
import com.project.proyectointermodularapp.data.repository.ResilientRequestRepository
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val requestRepository: RequestRepository
}

class DefaultAppContainer : AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val apiService: RequestructureApiService by lazy {
        retrofit.create(RequestructureApiService::class.java)
    }

    override val requestRepository: RequestRepository by lazy {
        ResilientRequestRepository(
            remoteRepository = NetworkRequestRepository(apiService),
            fallbackRepository = FakeRequestRepository()
        )
    }
}
