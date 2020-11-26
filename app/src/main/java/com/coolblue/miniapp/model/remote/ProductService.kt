package com.coolblue.miniapp.model.remote

import com.coolblue.miniapp.model.entities.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("search")
    suspend fun searchProduct(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ProductResponse>
}