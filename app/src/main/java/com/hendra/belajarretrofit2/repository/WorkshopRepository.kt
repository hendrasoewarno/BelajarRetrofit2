package com.hendra.belajarretrofit2.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hendra.belajarretrofit2.api_service.ApiClient
import com.hendra.belajarretrofit2.api_service.ApiInterface
import com.hendra.belajarretrofit2.model.APIResponse
import com.hendra.belajarretrofit2.model.Customer
import com.hendra.belajarretrofit2.model.Workshop
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class WorkshopRepository {

    companion object {

        var apiService: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        //Sync
        fun getWorkshop(context: Context, customer:Customer): LiveData<Workshop>? {

            var workshop: LiveData<Workshop>? = null;

            val call: Call<APIResponse?>? =
                apiService.getWorkshop(customer)

            try {
                //memanggil getSiswa secara sync
                val response: Response<APIResponse?> = call!!.execute() //Sync, MainThread
                val apiResponse: APIResponse? = response.body()
                if (apiResponse!!.status == "1") {
                    Log.d("TAG", "Response = ${apiResponse!!.message}")
                    Log.d("TAG", "Response = ${apiResponse!!.data}")
                    //convert data to data object
                    workshop = MutableLiveData(
                        Gson().fromJson(
                            apiResponse!!.data,
                            Workshop::class.java
                        )
                    )
                    Log.d("TAG", "Response = $customer")
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return workshop;
        }
    }
}