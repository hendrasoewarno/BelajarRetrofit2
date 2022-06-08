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
import com.hendra.belajarretrofit2.model.Species
import com.hendra.belajarretrofit2.model.Workshop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
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

        //ASync, untuk diproses callback
        fun uploadPhoto(context: Context, id: Int, file: File, callback: (count: Species) -> Unit) {
            val requestFile: RequestBody = file.asRequestBody("text/plain".toMediaTypeOrNull())

            //siapkan multipart/form-data
            val filePart: MultipartBody.Part =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile) //pada multer harus sesuai
            val idPart: RequestBody = id.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val call: Call<APIResponse?>? =
                apiService.uploadPhoto(idPart, filePart)

            //memanggil create secara async
            call!!.enqueue(object : Callback<APIResponse?> {
                override fun onResponse(
                    call: Call<APIResponse?>,
                    response: Response<APIResponse?>
                ) {
                    var apiResponse: APIResponse? = response.body()
                    if (apiResponse!!.status == "1") {
                        Log.d("TAG", "Response = ${apiResponse!!.message}");

                        var species: Species = Gson().fromJson(
                            apiResponse!!.data,
                            Species::class.java
                        )

                        callback(species) //dipanggil ke callback
                    }
                }

                override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                    Log.d("TAG", "Response = $t")
                }
            })
        }
    }
}