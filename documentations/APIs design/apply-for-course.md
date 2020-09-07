
## /courses/{courseId}/pre-registration/{preRegistrationId}/enrollment

## Method: PUT
#### API Access Roles: [LEARNER]

All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : all
* Type: String

## will save automatically
Variable  | Type | Required | Min Size | Max Size | Default value
------------- | ------------- | ------------- | ------------- | ------------- | -------------
creatorUserId  | String  | required | -- | -- | loggedUserId

---
Requested URL : http://localhost:33001/courses/{courseId}/pre-registration/{preRegistrationId}/enrollment<br>
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
          "isPaymentVerified": false,
          "paymentMethod": "bKash",
          "paid": "0",
          "due": "4000",
          "commitmentDuePaidDate": "20 july,2020"
        }

```
**Response:** <br>
Success: 200<br>
Error: 401

