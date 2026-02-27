package com.msnegi.hiltmvvm.retrofit

import com.msnegi.hiltmvvm.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface FakerAPI {

    @GET("products")
    suspend fun getProducts() : Response<List<Product>>
}