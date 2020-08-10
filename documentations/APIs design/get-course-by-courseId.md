
## /courses/{courseId}

## Method: GET
#### Access Permission: [ADMIN,INSTRUCTOR]
---
Requested URL : http://localhost:33001/courses/{courseId}<br>
--

Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
**Response:** <br>
status code:20o
```json
   {
      
      "courseId":"cdf34r-34r34r-f345tg345-g455",
      "creatorUserId":"rcdf34r-34r34r-f345tg345-g455",
      "createdDateAndTime":"1 july,2020 12:12",
      "lastModifiedDateAndTime":"1 july,2020 12:12",
      "isPublish":true,
      "courseType":"Web Development",
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
      "coursePriceInTk":"4000",
      "learners":[
         {
            "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
            "isPaymentVerified":false
         },
         {
            "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
            "isPaymentVerified":false
         },
         {
            "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
            "isPaymentVerified":false
         }
      ],
      "courseReviews":[
         {
            "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
            "review":"Very Good",
            "star":5
         },
         {
            "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
            "review":"Very Good",
            "star":5
         }
      ]
   }
```
