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
