
Request Header
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```

Request Param
```
"file" = "imagesURL"

"course" ="{
  "courseType": "Android Development",
  "courseSectionId": "970756",
  "courseName": "Java sith Basic",
  "courseDescription": "জাভা প্রগ্রামিং",
  "courseOutLineInPdfName": "জাভা প্রগ্রামিং",
  "whyDoThisCourse": "10",
  "courseOrientationDate": "15 july,2020",
  "courseStartingDate": "20 july,2020",
  "courseFinishingDate": "5 augest,2020",
  "courseTotalDuration": "15days",
  "courseNumberOfClasses": "7",
  "courseClassDuration": "2.5hrs",
  "youtubeEmbeddedLink": "https://www.youtube.com/watch?v=Ly7Z0ljOBd4",
  "courseClassTimeScheduleInPdf":"",
  "courseInstructorId": "324777",
  "coursePriceInTk": "4000",
  "offer": {
    "basicOfferInPercentage": "6",
    "specialOfferInPercentage": "5",
    "specialOfferReason": "COVID 19",
    "specialOfferStatDate": "20-01-2020",
    "specialOfferEndDate": "30-01-2020"
  }
}"
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
