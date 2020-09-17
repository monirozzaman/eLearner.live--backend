package live.elearners.controller;

import live.elearners.domain.model.Admin;
import live.elearners.domain.model.Instructors;
import live.elearners.dto.request.CourseOfferAddRequest;
import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.response.DashboardResponse;
import live.elearners.services.AdminService;
import live.elearners.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("admin")
@CrossOrigin("*")
public class AdminController {
    private final AuthService authService;
    private final AdminService adminService;

    @PutMapping("/courses/{courseId}/learners/{leanerId}/enrollment/verify")
    public ResponseEntity<Void> enrollmentVerify(HttpServletRequest httpServletRequest, @PathVariable String courseId, @PathVariable String leanerId) {
        authService.pink(httpServletRequest);
        return adminService.enrollmentVerify(courseId, leanerId);
    }

    @PutMapping("/courses/{courseId}/learners/{learnerId}/payment")
    public ResponseEntity<Void> updatePaymentInfo(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                  @PathVariable String learnerId, @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest) {
        authService.pink(httpServletRequest);
        return adminService.updatePaymentInfo(courseId, learnerId, learnersEnrollmentRequest);
    }

    @PutMapping("/courses/{courseId}/learners/{learnerId}/activation")
    public ResponseEntity<Void> updateCourseActivationStatus(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                             @PathVariable String learnerId, @RequestBody LearnerActiveRequest learnerActiveRequest) {
        authService.pink(httpServletRequest);
        return adminService.updateCourseActivationStatus(courseId, learnerId, learnerActiveRequest);
    }

    @GetMapping("/instructors")
    public ResponseEntity<List<Instructors>> getInstructors() {

        return adminService.getInstructors();
    }

    @PutMapping("/courses/{courseId}/offer")
    public ResponseEntity<String> addOffer(HttpServletRequest httpServletRequest, @PathVariable("courseId") String courseId, @RequestBody CourseOfferAddRequest courseOfferAddRequest) {
        authService.pink(httpServletRequest);
        return adminService.addOfferInCourse(courseId, courseOfferAddRequest);
    }

    @GetMapping()
    public ResponseEntity<List<Admin>> getAdminList(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return adminService.getAdminList();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardDetails() {

        return adminService.getDashboardDetails();
    }

}
