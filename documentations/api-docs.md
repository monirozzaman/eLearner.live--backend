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
[+] [ADMIN -POST /courses](APIs%20design/create-new-course.md)<br>
[+] [ALL -GET /courses](APIs%20design/get-All-courses.md)<br>
[+] [ALL -GET /courses/{courseId}](APIs%20design/get-course-by-courseId.md)<br>
[+] [ALL -GET /courses/sections/{courseSectionId}](APIs%20design/get-course-by-courseType.md)<br>
[+] [ADMIN -PUT /courses/{courseId}](APIs%20design/update-course.md)<br>
[+] [ADMIN -PUT /courses/{courseId}/publish](APIs%20design/publish-course-by-courseId.md)<br>
[+] [ADMIN -DELETE /courses/{courseId}](APIs%20design/delete-course-by-courseId.md)

#### *Sections APIs*
[+] [ADMIN -POST /sections](APIs%20design/create-sections.md)<br>
[+] [ALL -GET /sections](APIs%20design/get-All-sections.md)<br>
[+] [ALL -PUT /sections/{sectionId}](APIs%20design/update-section.md)<br>
[+] [ADMIN -DELETE /sections/{sectionId}](APIs%20design/delete-section.md)


#### *Learners APIs*
[+] [LEARNER -POST /courses/{courseId}/pre-registration](APIs%20design/pre-registration-post-for-course.md)<br>
[-] [LEARNER -DELETE /pre-registration/{preRegistrationId}](APIs%20design/pre-registration-for-course.md)<br>
[+] [LEARNER -UPDATE /courses/{courseId}/pre-registration/{preRegistrationId}/enrollment](APIs%20design/apply-for-course.md)<br>
[+] [ADMIN -UPDATE /courses/{courseId}/learners/{leanerId}/enrollment/verify](APIs%20design/enrollment-verfiy.md)<br>
[+] [ADMIN+INSTRUCTOR -GET /courses/{courseId}/learners](APIs%20design/get-learners-for-course.md)<br>
[+] [ADMIN -UPDATE /courses/{courseId}/learners/{learnerId}payment](APIs%20design/payment-for-course.md)<br>
[+] [ADMIN -UPDATE /courses/{courseId}/learners/{leanerId}/activation](APIs%20design/course-activation.md)

#### *Feedback APIs*
[+] [LEARNER+ADMIN -PUT /courses/{courseId}/review](APIs%20design/add-feedback-from-user.md)<br>
[+] [ADMIN -DELETE /courses/{courseId}/review/{reviewId}]()<br>
[-] [ADMIN -POST courses/{courseId}/top-review-courses](APIs%20design/add-top-reviewer.md)<br>
[-] [ALL -GET /top-review-courses](APIs%20design/get-top-reviewed-courses.md)<br>

#### *Sent Email APIs*
[-] [ALL -POST /courses/{courseId}/feedback-by-email](APIs%20design/send-email-by-courseId.md)<br>

#### *Developer APIs*
[-] [ADMIN -POST /partners](APIs%20design/partners-teams.md)<br>
[-] [ALL -GET /partners](APIs%20design/get-partners-teams.md)<br>
[-] [ADMIN -POST /developers](APIs%20design/developers-teams.md)<br>
[-] [ALL -GET /developers](APIs%20design/get-developers-teams.md)<br>
[-] [ADMIN -POST /instructors](APIs%20design/instructors-teams.md)<br>
[-] [ALL -GET /instructors](APIs%20design/get-instructors-teams.md)<br>

#### *Password Reset*
[-] [ALL -POST /reset?email=e@gmail.com](APIs%20design/password-reset.md)<br>
[-] [ALL -PUT /password/new-password/jghfsdfguydefvhjewfyusdvgfhsdfvhgugtfsdf](APIs%20design/upadte-newpassword.md)<br>
