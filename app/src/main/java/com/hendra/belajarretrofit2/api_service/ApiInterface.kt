package com.hendra.belajarretrofit2.api_service

import com.hendra.belajarretrofit2.model.APIResponse
import com.hendra.belajarretrofit2.model.Customer
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.GET
import java.io.File

interface ApiInterface {
    //@POST("api/v1/getWorkshop.php")
    @POST("api/v1/getWorkshop")
    fun getWorkshop(@Body customer: Customer?): Call<APIResponse?>?

    @Multipart
    @POST("api/v1/uploadPhoto")
    fun uploadPhoto(
        @Part("id") id: RequestBody?,
        @Part photo: MultipartBody.Part //name ditentukan pada implementasi
    ): Call<APIResponse?>?

}