# eLearner.live

___
## APIs Design
## Base port: 33001

#### *Login APIs*
[+] [POST /sign-up/learner](APIs%20design/signup-learner.md)<br>
[+] [POST /sign-up/instructor](APIs%20design/signup-instructor.md)<br>
[+] [POST /sign-up/admin](APIs%20design/signup-admin.md)<br>
[+] [GET /login](APIs%20design/login.md)<br>

#### *Courses APIs*
[+] [POST /courses](APIs%20design/create-new-course.md)<br>
[+] [GET /courses](APIs%20design/get-All-courses.md)<br>
[+] [GET /courses/{courseId}](APIs%20design/get-course-by-courseId.md)<br>
[-] [GET /courses/{courseType}](APIs%20design/get-course-by-courseType.md)<br>
[+] [PUT /courses/{courseId}](APIs%20design/update-course.md)<br>
[+] [PUT /courses/{courseId}/publish](APIs%20design/publish-course-by-courseId.md)<br>
[+] [DELETE /courses/{courseId}](APIs%20design/delete-course-by-courseId.md)


#### *Learners APIs*
[+] [UPDATE /courses/{courseId}/learners/{leanerId}/enrollment/verify](APIs%20design/enrollment-verfiy.md)<br>
[+] [UPDATE /courses/{courseId}/enrollment](APIs%20design/apply-for-course.md)<br>
[+] [POST /courses/{courseId}/pre-registration](APIs%20design/pre-registration-for-course.md)<br>
[-] [UPDATE /courses/{courseId}/payment](APIs%20design/payment-for-course.md)<br>
[-] [UPDATE /courses/{courseId}/activation/learners/{leanerId}/feedback-by-email](APIs%20design/course-activation.md)

#### *Feedback APIs*
[-] [PUT /courses/{courseId}/feedback](APIs%20design/add-feedback-from-user.md)<br>
[-] [POST /top-review-courses/add/courses/{courseId}](APIs%20design/add-top-reviewer.md)<br>
[-] [GET /top-review-courses](APIs%20design/get-top-reviewed-courses.md)<br>

#### *Sent Email APIs*
[-] [POST /courses/{courseId}/feedback-by-email](APIs%20design/send-email-by-courseId.md)<br>
[-] [POST /courses/{courseId}/learners/{learnerId}/feedback-by-email](APIs%20design/send-email-by-courseId_and_learnerId.md)<br>
[-] [POST /all/feedback-by-email](APIs%20design/send-email-for-all.md)<br>

#### *Developer APIs*
[-] [POST /developers](APIs%20design/developers-teams.md)<br>
[-] [GET /developers](APIs%20design/get-developers-teams.md)<br>
[-] [POST /instructors](APIs%20design/instructors-teams.md)<br>
[-] [GET /instructors](APIs%20design/get-instructors-teams.md)<br>

