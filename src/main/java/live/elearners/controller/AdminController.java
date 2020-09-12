package live.elearners.controller;

import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.services.AdminService;
import live.elearners.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin("*")
public class AdminController {
    private final AuthService authService;
    private final AdminService adminService;

    @PutMapping("/{courseId}/learners/{leanerId}/enrollment/verify")
    public ResponseEntity<Void> enrollmentVerify(HttpServletRequest httpServletRequest, @PathVariable String courseId, @PathVariable String leanerId) {
        authService.pink(httpServletRequest);
        return adminService.enrollmentVerify(courseId, leanerId);
    }

    @PutMapping("/{courseId}/learners/{learnerId}/payment")
    public ResponseEntity<Void> updatePaymentInfo(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                  @PathVariable String learnerId, @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest) {
        authService.pink(httpServletRequest);
        return adminService.updatePaymentInfo(courseId, learnerId, learnersEnrollmentRequest);
    }

    @PutMapping("/{courseId}/learners/{learnerId}/activation")
    public ResponseEntity<Void> updateCourseActivationStatus(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                             @PathVariable String learnerId, @RequestBody LearnerActiveRequest learnerActiveRequest) {
        authService.pink(httpServletRequest);
        return adminService.updateCourseActivationStatus(courseId, learnerId, learnerActiveRequest);
    }
}
