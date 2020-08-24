
## /courses/{courseId}/publish

## Method: PUT
#### API Access Roles: [ADMIN]

## will update automatically
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
isPublish  | boolean  | required | -- | -- | false






---
Requested URL : http://localhost:33001/courses/{courseId}/publish<br>
--
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
**Request body**
```json
{
  "isPublish": true
}
```
**Response:** <br>
status code:200

