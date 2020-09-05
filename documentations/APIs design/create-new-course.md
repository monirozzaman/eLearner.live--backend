
## /courses

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
Requested URL : http://localhost:33001/courses<br>
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
   "courseType":"Android Development",
   "courseSectionId":"970756",
"courseName":"Java sith Basic",
   "courseGoal":"জাভা প্রগ্রামিং",
   "courseMaxNumberOfLearner":"10",
   "courseOrientationDate":"15 july,2020",
   "courseStartingDate":"20 july,2020",
   "courseFinishingDate":"5 augest,2020",
   "courseTotalDuration":"15days",
   "courseNumberOfClasses":"7",
   "courseClassDuration":"2.5hrs",
   "courseClassTimeSchedule":{
      "saturdays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "sundays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "mondays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "tuesdays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "wednesdays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "thursdays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ],
      "fridays":[
         {
            "startTime":"10:10",
            "endTime":"12:10"
         }
      ]
   },
   "courseInstructorId":"324777",
   "coursePriceInTk":"4000",
   "coursePriceInOffer":"3000"
}
```
**Response:** <br>
status code:201
```json
{
   "courseId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
