
## /sections

## Method: POST
#### API Access Roles: [ADMIN]
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : all
* Type: String

Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
courseMaxNumberOfLearner  | int  | required | Default | Default | Default
learners  | object  | optional | Default | Default | Default
courseReviews  | object  | optional | Default | Default | Default
courseInstructorId  | String  | optional | Default | Default | Default
courseInstructorName  | String  | optional | Default | Default | Default
courseInstructorQualification  | String  | optional | Default | Default | Default
courseInstructorPhoneNumber  | String  | optional | Default | Default | Default
isPublish  | boolean  | required | -- | -- | False
creatorUserId  | String  | required | -- | -- | --
createdDateAndTime  | Date  | required | -- | -- | --

## will save automatically
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
creatorUserId  | String  | required | -- | -- | loggedUserId
createdDateAndTime  | Date  | required | -- | -- | currentDateTime
lastModifiedDateAndTime  | Date  | required | -- | -- | currentDateTime
isCourseDeletedByInstructor  | boolean  | required | -- | -- | false





---
Requested URL : http://localhost:33001/sections<br>
--
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
Request Body
```json
{
   "sectionName":"Web Design"
}
```
**Response:** <br>
status code:201
```json
{
    "sectionId": "542589"
}
```
