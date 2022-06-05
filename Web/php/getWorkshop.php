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
$customer->lon
*/

$workshop = <<<EOD
{
    "merek": "Bengkel555",
    "alamat": "Jl. Pangeran Jayakarta No. 123",
    "type": "car",
    "lat": 95.23456,
    "lon": 133.23456
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
