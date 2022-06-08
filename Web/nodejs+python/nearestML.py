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

