package com.example.userauth.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 120L

    private val okHttpClient = OkHttpClient.Builder().also { client ->
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        client.addInterceptor(httpLoggingInterceptor)
        client.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        client.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        client.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        client.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }.build()

    val instance: ServiceInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
        retrofit.create(ServiceInterface::class.java)
    }
}