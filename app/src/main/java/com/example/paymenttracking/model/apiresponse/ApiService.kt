package com.example.paymenttracking.model.apiresponse

import com.example.paymenttracking.model.ApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("?results=5&gender=male")
    suspend fun getUsers():Response<ApiResponse>
}
object RetroFitInstance{
    private const val BASE_URL = "https://randomuser.me/api/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}