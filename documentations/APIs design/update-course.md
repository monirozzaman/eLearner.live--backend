
##  /courses/{courseId}

## Method: PUT
#### API Access Roles: [ADMIN,INSTRUCTOR]
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : All required
* Type: String

## will update automatically
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
creatorUserId  | String  | required | -- | -- | loggedUserId
createdDateAndTime  | Date  | required | -- | -- | currentDateTime
lastModifiedDateAndTime  | Date  | required | -- | -- | currentDateTime


---
Requested URL : http://localhost:33001/courses/{courseId}<br>
---
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
Request Body
--
**For ADMIN Role**
```json
{
   "courseType":"Android Development1",
   "courseName":"Java Development",
   "courseGoal":"জাভা প্রগ্রামিং",
   "courseMaxNumberOfLearner":"10",
   "courseOrientationDate":"15 july,2020",
   "courseStartingDate":"20 july,2020",
   "courseFinishingDate":"5 augest,2020",
   "courseTotalDurationInDays":"15Days",
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
         },
           {
            "startTime":"14:10",
            "endTime":"12:10"
         },  {
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
            "endTime":"12:00"
         }
      ]
   },
   "courseInstructorId":"3e42ytr-2r2323r2-3re23r23-fd334r43",
   "courseInstructorName":"Moniruzzaman Roni",
   "courseInstructorQualification":"BSc. in sogfhfgftware engineer",
   "courseInstructorPhoneNumber":"01988841890",
   "coursePriceInTk":"4000",
   "coursePriceInOffer":"1000"
}
```

**For INSTRUCTOR Role**
```json
{
   "courseType":"Android Development1",
   "courseName":"Java Development",
   "courseGoal":"জাভা প্রগ্রামিং",
   "courseMaxNumberOfLearner":"10",
   "courseOrientationDate":"15 july,2020",
   "courseStartingDate":"20 july,2020",
   "courseFinishingDate":"5 augest,2020",
   "courseTotalDurationInDays":"15Days",
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
         },
           {
            "startTime":"14:10",
            "endTime":"12:10"
         },  {
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
            "endTime":"12:00"
         }
      ]
   }
}
```

**Response:** <br>
status code:200

