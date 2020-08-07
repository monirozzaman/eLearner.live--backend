
## /courses/add/new-course

## Method: POST
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Required : all
* Type: String

Variable  | Type | Required | Min Size | Max Size
------------- | ------------- | ------------- | ------------- | -------------
courseMaxNumberOfLearner  | int  | required | Default | Default
learners  | object  | optional | Default | Default
courseReviews  | object  | optional | Default | Default




---
Requested URL : http://ocalhost:33001/courses/add/new-course

Request Body
```json
{
   "courseName":"Java Development",
   "courseGoal":"জাভা প্রগ্রামিং",
   "courseMaxNumberOfLearner":"10",
   "courseOriantationDate":"15 july,2020",
   "courseStartingDate":"20 july,2020",
   "courseFinishingDate":"5 augest,2020",
   "courseTotalDuiration":"15days",
   "courseNumberOfClasses":"7",
   "courseClassDuiration":"2.5hrs",
   "courseInstractorId":"3e42ytr-2r2323r2-3re23r23-fd334r43",
   "courseInstractorName":"Moniruzzaman Roni",
   "courseInstractorQualification":"BSc. in software engineer",
   "courseInstractorPhoneNumber":"01988841890",
   "coursePriceInTk":"4000",
   "learners":[
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentValified":false
      },
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentValified":false
      },
      {
         "learnerId":"32c45325fd-32df54324-c5644y67-342d5634",
         "isPaymentValified":false
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
