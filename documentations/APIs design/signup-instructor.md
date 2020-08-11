
## /sign-up/instructor

## Method: POST
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : all
* Type: String

---
Requested URL : http://localhost:33001/sign-up/instructor<br>
--

Request Body
```json
{
   "name":"Moniruzzaman Roni",
   "email":"r@gmail.com",
   "currentAddress":"r@gmail.com",
   "qualificationInfo":{
      "qualification":"BSc in Software Engineering",
      "designation":"Faculty",
      "companyName":"IT Village",
      "totalProfessionalExperienceInYear":4
   },
   "phoneNo":"01988841890",
   "password":"01988841890",
   "roles":"[ADMIN,INSTRUCTOR]"
}
```
**Response:** <br>
status code:201
```json
{
   "instructorId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
