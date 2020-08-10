
## /courses/update/course/{courseId}

## Method: PUT
#### Access Permission: [ADMIN,INSTRUCTOR]
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : All Optional
* Type: String

## More requested update
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
creatorUserId  | String  | required | -- | -- | loggedUserId
createdDateAndTime  | Date  | required | -- | -- | currentDateTime
lastModifiedDateAndTime  | Date  | required | -- | -- | currentDateTime


---
Requested URL : http://localhost:33001/courses/update/course/{courseId}<br>
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
   "createDateAndTime": "1 august,2020 12:00PM",
   "courseType": "Web Development",
   "courseName":"Java Development",
   "courseGoal":"জাভা প্রগ্রামিং",
   "courseMaxNumberOfLearner":"10",
   "courseOrientationDate":"15 july,2020",
   "courseStartingDate":"20 july,2020",
   "courseFinishingDate":"5 augest,2020",
   "courseTotalDuration":"15days",
   "courseNumberOfClasses":"7",
   "courseClassDuration":"2.5hrs",
   "courseClassStartTime":"12:00PM",
   "courseClassEndTime":"02:30PM",
   "courseInstructorId":"3e42ytr-2r2323r2-3re23r23-fd334r43",
   "courseInstructorName":"Moniruzzaman Roni",
   "courseInstructorQualification":"BSc. in software engineer",
   "courseInstructorPhoneNumber":"01988841890",
   "coursePriceInTk":"4000"
}
```
**Response:** <br>
status code:200

