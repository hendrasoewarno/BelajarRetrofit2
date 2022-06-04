package com.hendra.belajarretrofit2.api_service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor;

class ApiClient {

    //Static class
    companion object {
        //10.0.2.2 adalah localhost untuk komputer PC, karena localhost sudah diambil alih oleh emulator
        //127.0.0.1 (localhost emulator) -> 10.0.2.2 (localhost PC)
        //lihat res->xml->network_security_config.xml

        val baseURL = "http://10.0.2.2/BelajarRetrofit2/"

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getClient(): Retrofit {
            //mengaktifkan logging pada OKHttp client, sebagai Retrofit client
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }
    }
}