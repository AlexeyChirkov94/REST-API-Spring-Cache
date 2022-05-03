# Test task "Telda" company - CRUD REST API application with using Spring Cache

This is simple application for making CRUD operation with regions, and caching 3 last requested regions. 

According the task, region entity have only 3 field, there are: id, name and abbreviated name

## Install the application
### If you use Maven you can use:
    
    mvn install 

After that you should put database (teldatest.sqlite) in the same folder as target jar file.

### Otherwise, if you use Docker you can use:

    docker pull 32391443/telda_test_app
    
    
## Run the application
### If you chose Maven install use: 
    java -jar teldatest-0.0.1-SNAPSHOT.jar
    
### If you chose Docker install use: 
    docker run -d -p 8181:8181 32391443/telda_test_app
    
By default, application based on 8181 port.

# REST API
The REST API to the example app is described below.


## Get list of Regions

### Request

`GET /api/region/`

    curl -i -H 'Accept:application/json' http://localhost:8181/api/region
    
### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2022 07:05:00 GMT

    [{"id":1,"name":"Saint-Peterburg","abbreviatedName":"SPB"},
    {"id":2,"name":"Moscow","abbreviatedName":"MSK"},
    {"id":3,"name":"Ekaterinburg","abbreviatedName":"EKB"},
    {"id":4,"name":"Novosibirsk","abbreviatedName":"NSK"},
    {"id":5,"name":"New-Youk","abbreviatedName":"NY"},
    {"id":6,"name":"Los-Angeles","abbreviatedName":"LA"}]
    
    
## Create a new Region

### Request

`POST /api/region/`

    for unix bash:
    curl -i -X POST http://localhost:8181/api/region -H "Content-Type:application/json" -d '{"name":"My Sity", "abbreviatedName":"MS"}'
    
    for windows cmd:
    curl -i -X POST http://localhost:8181/api/region -H "Content-Type:application/json" -d "{\"name\": \"My Sity\",  \"abbreviatedName\" : \"MS\" }" 
### Response

    HTTP/1.1 200
    Content-Length: 0
    Date: Tue, 26 Apr 2022 07:14:46 GMT
    
    
## Create repeatable Region

In my application you cannot have more than one region with same name. And if you try to add one more region with same name. you will get exception message.

### Request

`POST /api/region/`

    for unix bash:
    curl -i -X POST http://localhost:8181/api/region -H "Content-Type:application/json" -d '{"name":"My Sity", "abbreviatedName":"MS"}'
    
    for windows cmd:
    curl -i -X POST http://localhost:8181/api/region -H "Content-Type:application/json" -d "{\"name\": \"My Sity\",  \"abbreviatedName\" : \"MS\" }" 

### Response

    HTTP/1.1 400
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2022 07:19:17 GMT
    Connection: close

    {"message":"Region with same name already registered"}

## Get a specific Region

Here works Spring cache. If now you  make some repeatable requests, application will not go to database. It will get response from the cache.

### Request

`GET /api/region/id`

    curl -i -H 'Accept:application/json' http://localhost:8181/api/region/1

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2022 07:20:35 GMT

    {"id":1,"name":"Saint-Peterburg","abbreviatedName":"SPB"}
    
    
## Get a non-existent Region

### Request

`GET /api/region/id`

    curl -i -H 'Accept:application/json' http://localhost:8181/api/region/100

### Response

    HTTP/1.1 404
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2022 07:21:24 GMT

    {"message":"There no region with id: 100"}
    
## Update Region

Lest update Saint-Peterburg to Saint-P.
Updated value in the database we update the values in the cache too

### Request

`PUT /api/region`

        for unix bash:
        curl -i -X PUT http://localhost:8181/api/region -H "Content-Type:application/json" -d '{"id":"1", "name":"Saint-P", "abbreviatedName":"SPB"}'
       
        
        for windows cmd:
        curl -i -X PUT http://localhost:8181/api/region -H "Content-Type:application/json" -d "{\"id\": \"1\", \"name\": \"Saint-P\",  \"abbreviatedName\" : \"SPB\" }" 

### Response

    HTTP/1.1 200
    Content-Length: 0
    Date: Tue, 26 Apr 2022 07:32:43 GMT
    
    {"id":1,"name":"Saint-P","abbreviatedName":"SPB"}
    
    
## Delete Region

Let`s delete My Sity.
Also this regions will be remove from cache.

### Request

`DELETE /api/region/id`

        curl -i -X DELETE http://localhost:8181/api/region/7
       

### Response

    HTTP/1.1 200
    Content-Length: 0
    Date: Tue, 26 Apr 2022 07:37:24 GMT
    
    
### That`s all, thank you for reading