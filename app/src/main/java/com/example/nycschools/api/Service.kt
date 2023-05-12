package com.example.nycschools.api

import com.example.nycschools.response.NYCSchoolItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

/**
 * Used to connect to the API to fetch data
 */
interface Service {
    @GET("resource/f9bf-2cp4.json")
    suspend fun getNYSSchoolsList():List<NYCSchoolItem>
    companion object {
        private const val BASE_URL = "https://data.cityofnewyork.us/"
        fun create(): Service {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service::class.java)
        }
    }
}