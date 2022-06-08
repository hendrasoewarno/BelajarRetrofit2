package com.hendra.belajarretrofit2.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hendra.belajarretrofit2.model.Customer
import com.hendra.belajarretrofit2.model.Species
import com.hendra.belajarretrofit2.model.Workshop
import com.hendra.belajarretrofit2.repository.WorkshopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/*
Dalam hal ini LoginViewModel kita merepresentasikan ViewModel yang
berinteraksi dengan view dan repositories.

View <-> ViewModel <-> Repositories <-> Room(Dao <-> Model <-> Database)
 */
class WorkshopViewModel : ViewModel() {

    //kita menggunakan suspend agar coroutine disuspend sampai join, baru mengembalikan hasil Web Request
    suspend fun getWorkshop(context: Context, customer: Customer): LiveData<Workshop>? {
        var liveDataWorkshop: LiveData<Workshop>? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            liveDataWorkshop = WorkshopRepository.getWorkshop(context, customer)
        }
        job.join() //wait until child coroutine completes
        return liveDataWorkshop
    }

    fun uploadPhoto(context: Context, id: Int, file: File,  callback: (count: Species) -> Unit) {
        WorkshopRepository.uploadPhoto(context, id, file, callback);
    }
}

