package com.hendra.belajarretrofit2.model
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/*
Deklarasi model untuk Entity Siswa
 */

//ini adalah model untuk Customer untuk dikirim ke backend (web)
/*
Contoh json

{
    type: "car",
    lat: 95.12345,
    long: 133.12345
}
 */

data class Customer(
    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "lat")
    var lat: Double? = null,

    @ColumnInfo(name = "lon")
    var long: Double? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}

//ini adalah model untuk Workshop yang dikembalikan dari backend (web)
/*
Contoh json

{
    "merek": "Bengkel555",
    "alamat": "Jl. Pangeran Jayakarta No. 123"
    "type": "car"
    "lat": 95.23456,
    "long": 133.23456
}
 */
data class Workshop(
    @ColumnInfo(name = "merek")
    var merek: String? = null,

    @ColumnInfo(name = "alamat")
    var alamat: String? = null,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "lat")
    var lat: Double? = null,

    @ColumnInfo(name = "lon")
    var long: Double? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}

/*
Deklarasi data class untuk Model APIResponse
 */
data class APIResponse(
    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "message")
    var message: String? = null,

    @ColumnInfo(name = "data")
    var data: String? = null,
) {

}

