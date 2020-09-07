
##  /courses/{courseId}

## Method: PUT


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
  "courseId": "dfge4564-546t45t",
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
  "youtubeEmbeddedLink": "2.5hrs",
  "courseClassTimeScheduleInPdf": "",
  "courseInstructorId": "324777",
  "coursePriceInTk": "4000"
}
```

**Response:** <br>
**status code:**<br>
Success: 201 <br>
Error: 501,404,401 <br>


