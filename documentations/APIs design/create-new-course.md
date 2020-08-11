
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
   "isPublish": true,
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
   "coursePriceInTk":"4000",
   "learners":[
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentVerified":false,
          "paymentMethod": "bKash",
          "paid": "1000",
          "due": "3000",
          "commitmentDuePaidDate": "20 july,2020" 
      },
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentVerified":false,
          "paymentMethod": "bKash",
          "paid": "0",
          "due": "4000",
          "commitmentDuePaidDate": "20 july,2020"  
      },
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentVerified":false,
          "paymentMethod": "bKash",
          "paid": "0",
          "due": "4000",
          "commitmentDuePaidDate": "20 july,2020" 
      }
   ],
   "courseReviews":[
      {
         "name":"Hasib",
         "designation":"Engineer",
         "email": "",
         "phoneNumber": "",
         "review":"Very Good",
         "star":5
      },
      {
         "name":"roni",
         "designation":"Engineer",
         "email": "",
         "phoneNumber": "",
         "review":"Very Good",
         "star":5
      }
   ]
}
```
**Response:** <br>
status code:201
```json
{
   "courseId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
