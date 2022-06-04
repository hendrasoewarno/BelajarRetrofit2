package com.hendra.belajarretrofit2.api_service

import com.hendra.belajarretrofit2.model.APIResponse
import com.hendra.belajarretrofit2.model.Customer
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.GET

interface ApiInterface {
    //@POST("api/v1/getWorkshop.php")
    @POST("api/v1/getWorkshop")
    fun getWorkshop(@Body customer: Customer?): Call<APIResponse?>?
}