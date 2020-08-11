
## /sign-up/admin

## Method: POST
All variable follow  default expects below table
* Default Min Size : 3
* Default Max Size : 30
* Default Value : from user
* Required : all
* Type: String

---
Requested URL : http://localhost:33001/sign-up/admin<br>
--

Request Body
```json
{
  "name": "Moniruzzaman Roni",
  "email": "r@gmail.com",
  "currentAddress": "r@gmail.com",
  "presentWorkField": "Engineer",
  "phoneNo": "01988841890",
  "password": "01988841890",
  "roles":"[ADMIN]"
  
}
```
**Response:** <br>
status code:201
```json
{
   "adminId":"tg34e56345-3456fg345-34fg5643-346g"
}
```
