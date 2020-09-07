
##  /developers

## Method: POST
#### API Access Roles: [ADMIN]
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : All 
* Type: String


---
Requested URL : http://localhost:33001/developers<br>
---
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
Request Pram
```
"file" = "imagesURL"

  "developer"= {
      "name":"Mahidul Moon",
      "designation":"Mahidul Moon",
      "qualification":"BSc in Software Engineer",
      "about":"",
      "workYear":"2020-2021"
   }
```
**Response:** <br>
status code:200

