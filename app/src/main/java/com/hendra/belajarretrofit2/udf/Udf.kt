package com.hendra.belajarretrofit2.udf

import android.content.Context
import okhttp3.ResponseBody
import java.io.*

class Udf {
    companion object {
        fun fileSave(context: Context, body: ResponseBody, fname: String): Boolean {
            return try {
                // todo change the file location/name according to your needs
                val futureStudioIconFile =
                    File(context.getFilesDir(), fname)
                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null
                try {
                    val fileReader = ByteArray(4096)
                    var fileSizeDownloaded: Long = 0
                    inputStream = body.byteStream()
                    outputStream = FileOutputStream(futureStudioIconFile)
                    while (true) {
                        val read: Int = inputStream.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        outputStream.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()
                    }
                    outputStream.flush()
                    true
                } catch (e: IOException) {
                    false
                } finally {
                    if (inputStream != null) {
                        inputStream.close()
                    }
                    if (outputStream != null) {
                        outputStream.close()
                    }
                }
            } catch (e: IOException) {
                false
            }
        }
    }
}