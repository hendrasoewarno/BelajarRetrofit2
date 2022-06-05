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

Pada contoh ini akan memanggil url:

http://192.168.100.13:3000/api/v1/getWorkshop.php

getWorkshop.js
```
/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body(json),
dan mempersiapkan dummy response
*/
var express = require('express')
var app = express()
var port = 3000

// Multiple routing
var getWorkshop = express.Router()

getWorkshop.use(express.json())
getWorkshop.post('/api/v1/getWorkshop', function (req, res, next) {
    console.log("getWorkshop Working")

    //untuk mengakses masing-masing req
    console.log(req.body.type)
    console.log(req.body.lat)
    console.log(req.body.long)

    //dummy workshop response, nanti disini diganti dengan hasil dari ML
    var workshop = {
	"merek": "Bengkel555",
	"alamat": "Jl. Pangeran Jayakarta No. 123",
	"type": "car",
	"lat": 95.23456,
	"long": 133.23456
    }

    res.send(
	{
		"status":1,
		"message": "berhasil",
		"data": JSON.stringify(workshop)
	})
    res.end()
});
 
app.use(getWorkshop)
 
app.listen(port, () => {
    console.log(`Workshop web listening at http://localhost:${port}`)
});
```
