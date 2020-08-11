
## /top-review-courses/add/courses/{courseId}

## Method: POST

#### API Access Roles: [ADMIN]

#### Requested URL : http://localhost:33001/top-review-courses/add/courses/{courseId}<br>


**Request Header**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9
                .eyJzdWIiOiJ1c2VyMSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1MjYzOTA0NDMsImV4cCI6MTUyNjQwODQ0M30
                .4uWqKGkyP7TJu_W2M0apZqK6CLrM8bgl3uolo2piHmQ
```
**Request body**
```json
 {
         "numberOfLearners":10,
         "averagesOfStars":4.9,
         "topReviewer":
            {
               "name":"Hasib",
               "designation":"Engineer",
               "email":"",
               "phoneNumber":"",
               "review":"Very Good",
               "star":5
            }
      }
```
**Response:** <br>
status code:200

