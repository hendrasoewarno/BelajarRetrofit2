# BelajarRetrofit2
Contoh Koding komunikasi web dengan Retrofit2 dengan Model MVVM

## PHP
Pada contoh ini akan memanggil url:

http://10.0.2.2/BelajarRetrofit2/api/v1/getWorkshop.php

Dengan payload:

{"type":"Car", "lat":95.12345, "lon":133.12345}

dan diresponse dengan:

{
"status":"1",
"message":"Berhasil",
"data":
"{\"merek\":\"Bengkel555\",\"alamat\": \"Jl. Pangeran Jayakarta No. 123\",\"type\": \"car\",\"lat\": 95.23456,\"lon\": 133.23456}"
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
```

## NodeJS
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
    console.log(req.body.lon)

    //dummy workshop response, nanti disini diganti dengan hasil dari ML
    var workshop = {
	"merek": "Bengkel555",
	"alamat": "Jl. Pangeran Jayakarta No. 123",
	"type": "car",
	"lat": 95.23456,
	"lon": 133.23456
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

## NodeJS dengam koneksi database MYSQL
```
CREATE DATABASE `dataset`;
USE `dataset`;
CREATE TABLE `workshop` (
  `merek` varchar(50) NOT NULL,
  `alamat` tinytext NOT NULL,
  `type` varchar(50) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `workshop`
  ADD PRIMARY KEY (`id`);
  
INSERT INTO `workshop` (`merek`, `alamat`, `type`, `lat`, `lon`, `id`) VALUES
('Bengkel555', 'Jl. Pangeran Jayakarta No. 123', 'car', 95.23456, 133.23456, 1),
('Bengkel666', 'Jl. Mangga Besar No. 123', 'car', 95.13456, 133.13456, 2);
```

getWorkshop1.js
```
/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body(json),
dan mempersiapkan response dari dataset tersimpan di MySQL
*/
var express = require('express')
var app = express()
var port = 3000

//inisialisasi koneksi database
var mysql = require('mysql')
var con = mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'yourpassword',
        port: 3306,
});

con.connect(function(err) {
        if (err) throw err
        console.log('Database Connected!')
})


// Multiple routing
var getWorkshop = express.Router()

getWorkshop.use(express.json())

getWorkshop.post('/api/v1/getWorkshop', function (req, res, next) {
	console.log("getWorkshop Working")

	//untuk mengakses masing-masing req
	console.log(req.body.type)
	console.log(req.body.lat)
	console.log(req.body.lon)

	//dummy workshop response, nanti disini diganti dengan hasil dari ML

	var workshop;

	//sementara kita ganti response dari mysql langsung

	con.query("SELECT merek, alamat, lat as lat, lon as lon FROM dataset.workshop WHERE type like ? ORDER BY ABS(lat-?)+ABS(lon-?) LIMIT 1",
		['%'+req.body.type+'%', req.body.lat, req.body.lon],
		 function (err, result, fields) {

		if (err) throw (err)
			
		if (result.length>0) {
			workshop = result[0]

			res.send(
		        {
        	        	"status":1,
	        	        "message": "berhasil",
        	        	"data": JSON.stringify(workshop)
		        })
		}
		else {
			res.send(
			{
				"status":0,
				"message": "tidak ada bengkel terdekat untuk jenis kendaraan anda",
				"data": null
			})
		}

		res.end()
	})
})

app.use(getWorkshop)

app.listen(port, () => {
    console.log(`Workshop web listening at http://localhost:${port}`)
});
```

## NodeJS dengan Python

getWorkshop2.js
```
/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body(json),
dan mengambil hasil dari Python sebagai response
*/
var express = require('express')
var {spawn} = require('child_process')
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
    console.log(req.body.lon)

    //dummy workshop response, nanti disini akan memanggil ML 
    //dengan spawn process baru untuk memanggil script python
    var largeDataSet = []

    const callPython = spawn('python3', ['nearestML.py', req.body.type, req.body.lat, req.body.lon])

    //menerima data dari hasil pemanggilan
    callPython.stdout.on('data', function(data) {
	largeDataSet.push(data)
    });

    //response ke client web dari close event (stream dari callPython sudah ditutup
    callPython.on('close', function(code)  {
	var result = largeDataSet.join()
	res.send(result)
	res.end()
    })

});

app.use(getWorkshop)

app.listen(port, () => {
    console.log(`Workshop web listening at http://localhost:${port}`)
});
```

nearestML.py
```
import sys
import json
#print(sys.argv[1])
#print(sys.argv[2])
#print(sys.argv[3])

#dummy workshop response, nanti disini diganti dengan hasil dari ML
workshop = {
        "merek": "Bengkel555",
        "alamat": "Jl. Pangeran Jayakarta No. 123",
        "type": "car",
        "lat": 95.23456,
        "lon": 133.23456
    }

response = {
	"status":1,
	"message": "berhasil",
	"data": json.dumps(workshop)
    }

print(json.dumps(response))
```
