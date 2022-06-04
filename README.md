# BelajarRetrofit2
Contoh Koding komunikasi web dengan Retrofit2 dengan Model MVVM

Pada contoh ini akan memanggil url:

http://10.0.2.2/BelajarRetrofit2/api/v1/getWorkshop.php

Dengan payload:

{"type":"Car", "lat":95.12345, "long":133.12345}

dan diresponse dengan:

{
"status":"1",
"message":"Berhasil",
"data":
"{\"merek\":\"Bengkel555\",\"alamat\": \"Jl. Pangeran Jayakarta No. 123\",\"type\": \"car\",\"lat\": 95.23456,\"long\": 133.23456}"
}

getWorkshop.php
```
<?php
/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body,
dan mempersiapkan dummy response
*/

$body = file_get_contents('php://input'); //baca request body
$customer = json_decode($body); //ubah ke objek customer

/*
Untuk mengakses masing-masing key pada customer
$customer->type
$customer->lat
$customer->long
*/

$workshop = <<<EOD
{
    "merek": "Bengkel555",
    "alamat": "Jl. Pangeran Jayakarta No. 123",
    "type": "car",
    "lat": 95.23456,
    "long": 133.23456
}
EOD;

$data = json_encode($workshop); //ubah ke objek customer

$response = <<<EOD
{
	"status":"1",
	"message":"Berhasil",
	"data":$data
}
EOD;
echo $response;
?>
```
