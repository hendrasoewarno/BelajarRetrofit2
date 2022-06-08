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
