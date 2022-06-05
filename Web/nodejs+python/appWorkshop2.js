/*
Ini hanya Stub untuk mensimulasikan sisi server
yang menerima POST Body(json),
dan mempersiapkan dummy response
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
