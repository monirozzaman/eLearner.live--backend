package live.elearners.controller;

import live.elearners.domain.model.Learners;
import live.elearners.dto.request.PaymentInfoRequest;
import live.elearners.dto.response.PaymentStepStatusResponse;
import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.dto.response.PreRegistrationWithDetailsResponse;
import live.elearners.services.AuthService;
import live.elearners.services.LearnersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("learners")
@CrossOrigin("*")
public class LearnersController {
    private final AuthService authService;
    private final LearnersService learnersService;

//    @PutMapping("/pre-registration/{preRegistrationId}/enrollment")
//    public ResponseEntity<Void> enrollment(HttpServletRequest httpServletRequest,
//                                           @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest,
//                                           @PathVariable String preRegistrationId) {
//        authService.pink(httpServletRequest);
//        return learnersService.enrollment(learnersEnrollmentRequest, preRegistrationId);
//    }

    @PutMapping("/courses/{courseId}/pre-registration/{step}")
    public ResponseEntity<PreRegistrationResponse> updateCourseById(HttpServletRequest httpServletRequest,
                                                                    @PathVariable String courseId,
                                                                    @PathVariable String step) {
        authService.pink(httpServletRequest);
        return learnersService.updatePaymentStep(courseId, step);
    }

    @GetMapping("/pre-registration/courses")
    public ResponseEntity<List<PreRegistrationWithDetailsResponse>> getPreRegistrationCourses(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return learnersService.getPreRegistrationCourses();
    }

    @DeleteMapping("/pre-registration/{preRegistrationId}")
    public ResponseEntity<Void> deletePreRegistrationByCourseId(HttpServletRequest httpServletRequest, @PathVariable String preRegistrationId) {
        authService.pink(httpServletRequest);
        return learnersService.deletePreRegistrationByCourseId(preRegistrationId);
    }

    @GetMapping("/{learnerId}")
    public ResponseEntity<Learners> getLearnerByLearnerId(@PathVariable("learnerId") String learnerId) {

        return learnersService.getLearnerByLearnerId(learnerId);
    }

    @GetMapping("")
    public ResponseEntity<List<Learners>> getLearners(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return learnersService.getLearners();
    }

    @PutMapping("/courses/{courseId}/enrollment/{enrollmentStepNo}")
    public ResponseEntity<Void> updateStepNo(HttpServletRequest httpServletRequest,
                                             @PathVariable("courseId") String courseId,
                                             @PathVariable("enrollmentStepNo") String enrollmentStepNo) {
        authService.pink(httpServletRequest);
        return learnersService.updateStepNo(courseId, enrollmentStepNo);
    }

    @PutMapping("courses/{courseId}/enrollment/payment")
    public ResponseEntity<Void> updateStepStatus(HttpServletRequest httpServletRequest,
                                                 @RequestBody PaymentInfoRequest paymentInfoRequest,
                                                 @PathVariable String courseId) {
        authService.pink(httpServletRequest);
        return learnersService.updatePaymentStatus(courseId, paymentInfoRequest);
    }

    @GetMapping("courses/{courseId}/paymentStepStatus")
    public ResponseEntity<PaymentStepStatusResponse> getPaymentStepStatus(HttpServletRequest httpServletRequest,
                                                                          @PathVariable("courseId") String courseId) {
        authService.pink(httpServletRequest);
        return learnersService.getPaymentStepStatus(courseId);
    }

}
