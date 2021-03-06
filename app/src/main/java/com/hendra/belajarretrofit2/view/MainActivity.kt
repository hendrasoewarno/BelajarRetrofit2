package com.hendra.belajarretrofit2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.hendra.belajarretrofit2.R
import com.hendra.belajarretrofit2.model.APIResponse
import com.hendra.belajarretrofit2.model.Customer
import com.hendra.belajarretrofit2.model.Workshop
import com.hendra.belajarretrofit2.viewmodel.WorkshopViewModel
import kotlinx.coroutines.*
import android.os.Environment
import android.util.Log
import java.io.File


class MainActivity : AppCompatActivity() {
    var apiResponse: APIResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        //ini akses melalui viewmodel untuk menghindari akses ke API Interface secara langsung
        var workshopViewModel = WorkshopViewModel()


        var customer: Customer = Customer(type = "Car", lat = 95.12345, lon = 133.12345)

        //suatu suspend fun harus dipanggil dalam runBlocking
        runBlocking {
            var workshop: LiveData<Workshop>? =
                workshopViewModel.getWorkshop(baseContext, customer) //suspend function
            workshop!!.observe(this@MainActivity, Observer {
                if (it != null) {
                    //Disini anda mengupdate ke View, dalam hal ini kita cetak saja hasil ke console
                    println(it.type + " " + it.merek + " " + it.lat + " " + it.lon)
                }
            })
        }

        var dummy = File(baseContext.getFilesDir(), "dummy.txt")
        dummy.writeText("Hello World")

        //disini kita menggunakan teknik callback untuk mengupdate view
        workshopViewModel.uploadPhoto(baseContext, 1, dummy
        ) { species ->
            println(species.name)
            println(species.description)
        }
    }
}