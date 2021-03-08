package ru.carwash.controllers

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RequestController private constructor() {

    private lateinit var retrofit: Retrofit
    private val baseUrl = "http://192.168.0.21:3000"

    companion object {
        val instance = RequestController()
    }

    fun getApiService(context: Context): ServerApi {
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
        return retrofit.create(ServerApi::class.java)
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
    }
}