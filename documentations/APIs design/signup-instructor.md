
*Table field:*
```json
{
  "isActive": true,
  "imageName": "a.jpg"
}
```
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
   "password":"01988841890"
}
```
**Response:** <br>
```json
{
   "instructorId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
** status code: **<br>
Success: 201<br>
Error: 406 Not Acceptable<br>
