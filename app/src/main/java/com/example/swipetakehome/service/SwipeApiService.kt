package com.example.swipetakehome.service

import retrofit2.http.GET

interface SwipeApiService {

    @GET("public/take_home_sample_profiles")
    suspend fun getAll(): ApiResponse

}