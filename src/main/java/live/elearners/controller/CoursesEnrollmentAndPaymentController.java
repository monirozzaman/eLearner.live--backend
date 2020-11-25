package live.elearners.controller;

import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.request.PaymentInfoRequest;
import live.elearners.dto.response.PaymentStepStatusResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseEnrollmentAndPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("api")
@CrossOrigin("*")
public class CoursesEnrollmentAndPaymentController {

    private final AuthService authService;
    private final CourseEnrollmentAndPaymentService courseEnrollmentAndPaymentService;


    /*
     * GET Mapping
     * */
    @GetMapping("/courses/{courseId}/enrollmentStep")
    public ResponseEntity<PaymentStepStatusResponse> getPaymentStepStatus(HttpServletRequest httpServletRequest,
                                                                          @PathVariable("courseId") String courseId) {
        authService.pink(httpServletRequest);
        return courseEnrollmentAndPaymentService.getEnrollmentStepStatus(courseId);
    }

    /*
     * PUT Mapping
     * */
    @PutMapping("/courses/{courseId}/enrollment/{enrollmentStepNo}")
    public ResponseEntity<Void> updateEnrollmentStepNo(HttpServletRequest httpServletRequest,
                                                       @PathVariable("courseId") String courseId,
                                                       @PathVariable("enrollmentStepNo") String enrollmentStepNo) {
        authService.pink(httpServletRequest);
        return courseEnrollmentAndPaymentService.updateEnrollmentStepNo(courseId, enrollmentStepNo);
    }

    @PutMapping("/courses/{courseId}/enrollment/payment")
    public ResponseEntity<Void> updatePaymentStatusByCourseId(HttpServletRequest httpServletRequest,
                                                              @RequestBody PaymentInfoRequest paymentInfoRequest,
                                                              @PathVariable String courseId) {
        authService.pink(httpServletRequest);
        return courseEnrollmentAndPaymentService.updatePaymentStatusByCourseId(courseId, paymentInfoRequest);
    }

    @PutMapping("/courses/{courseId}/learners/{leanerId}/enrollment/verify")
    public ResponseEntity<Void> enrollmentVerify(HttpServletRequest httpServletRequest, @PathVariable String courseId, @PathVariable String leanerId) {
        authService.pink(httpServletRequest);
        return courseEnrollmentAndPaymentService.enrollmentVerify(courseId, leanerId);
    }

    @PutMapping("/courses/{courseId}/learners/{learnerId}/payment")
    public ResponseEntity<Void> updatePaymentInfo(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                  @PathVariable String learnerId, @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest) {
        authService.pink(httpServletRequest);
        return courseEnrollmentAndPaymentService.updatePaymentInfo(courseId, learnerId, learnersEnrollmentRequest);
    }
}
