/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body(json),
dan mempersiapkan dummy response
*/
var express = require('express')
var multer = require('multer')
const os = require('os')
const fs = require('fs')

var app = express()
var port = 3000

// Multiple routing
var getIndex = express.Router()
var getWorkshop = express.Router()
var uploadPhoto = express.Router()

getWorkshop.use(express.json())

getIndex.get('/', function (req, res, next) {
    console.log("getIndex")
    res.send("Hello World")
    res.end()
})

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


const upload = multer({ dest: os.tmpdir() });

uploadPhoto.post('/api/v1/uploadPhoto', upload.single('photo'),  (req, res, next) => {

    const photo = req.file
    const id = req.body.id

    if (!photo) {
        res.status(400).send({status: false, data: "No Photo upload"})
    }

    console.log(photo);
    console.log("id=" + id);

    var species = {
	"name" : "Mazda 2",
	"description" : "Mazda 2 merupakan kendaraan Hatch Back"
    }

    res.send(
        {
                "status":1,
                "message": "berhasil",
                "data": JSON.stringify(species)
        })

})

app.use(getIndex)
app.use(getWorkshop)
app.use(uploadPhoto)

app.listen(port, () => {
    console.log(`Workshop web listening at http://localhost:${port}`)
});
