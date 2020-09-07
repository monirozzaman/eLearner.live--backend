
##  /courses/{courseId}/payment

## Method: PUT
#### API Access Roles: [ADMIN,LEARNER]
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : All required
* Type: String

Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
commitmentDuePaidDate  | Date  | optional | -- | - -| --


## will update automatically
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
learnerId  | String  | required | -- | -- | loggedUserId


---
Requested URL : http://localhost:33001/courses/{courseId}/payment<br>
---
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
Request Body
```json
        {      
          "paid": "0",
          "due": "4000",
          "commitmentDuePaidDate": "20 july,2020"
        }
```
**Response:** <br>
status code:200

