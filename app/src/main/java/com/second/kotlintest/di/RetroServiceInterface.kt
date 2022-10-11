package com.second.kotlintest.di

import com.second.kotlintest.model.Result
import com.second.kotlintest.model.PostForm
import com.second.kotlintest.model.RecyclerList
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("meta")
    suspend fun getDataFromAPI(): RecyclerList

    @POST("data/")
    suspend fun sendRequest(@Body sendRequestBody: PostForm): Result
}