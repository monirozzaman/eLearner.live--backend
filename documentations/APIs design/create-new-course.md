
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```

Request Param
```
"file" = "imagesURL"

"courseRequestInString" ={
          "courseSectionId":"540424",
             "courseName":"React543t534tith Basic",
             "courseBasicDescription":"React543t534tith Basic",
             "courseWhyDo":"React543t534tith Basic",
             "courseGoal":"জাভা প্রগ্রাfghfghfghমিং",
             "courseMaxNumberOfLearner":"10",
             "courseOrientationDate":"15 july,2020",
             "courseStartingDate":"20 july,2020",
             "courseFinishingDate":"5 augest,2020",
             "courseTotalDurationInDays":"10days",
             "courseNumberOfClasses":"70",
             "courseClassDuration":"2.50000hrs",
          "youtubeEmbeddedLink": "https://www.youtube.com/watch?v=Ly7Z0ljOBd4",
             "courseClassTimeScheduleRequests":[
          {"day":"Sunday",
          "start_time":"12:00",
          "end_time":"1:00"}
          ],
             "courseInstructorId":"484416",
             "coursePriceInTk":"4000",
               "offer": {
              "basicOfferInPercentage": "6",
              "specialOfferInPercentage": "5",
              "specialOfferReason": "COVID 19",
              "specialOfferStatDate": "20-01-2020",
              "specialOfferEndDate": "30-01-2020"
            }
          }

```
**Response:**
```json
{
   "courseId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
**status code:**<br>
Success: 201 <br>
Error: 204,401 <br>
