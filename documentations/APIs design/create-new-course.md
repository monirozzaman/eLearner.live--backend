
## /courses/add/new-course

## Method: POST
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
isPublish  | boolean  | required | none | none | True




---
Requested URL : http://ocalhost:33001/courses/add/new-course

Request Body
```json
{
   "createByInstructorId": "f65d2s562-675vs257-sd765v",
   "createDateAndTime": "1 august,2020 12:00PM",
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
Response ( 200OK)
```json
{
   "courseId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
