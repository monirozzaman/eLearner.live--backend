package live.elearners.controller;

import live.elearners.domain.model.Learners;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.services.AuthService;
import live.elearners.services.LearnersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("learners")
@CrossOrigin("*")
public class LearnersController {
    private final AuthService authService;
    private final LearnersService learnersService;

    @PutMapping("/pre-registration/{preRegistrationId}/enrollment")
    public ResponseEntity<Void> enrollment(HttpServletRequest httpServletRequest,
                                           @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest,
                                           @PathVariable String preRegistrationId) {
        authService.pink(httpServletRequest);
        return learnersService.enrollment(learnersEnrollmentRequest, preRegistrationId);
    }

    @PostMapping("/courses/{courseId}/pre-registration")
    public ResponseEntity<PreRegistrationResponse> updateCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId) {
        authService.pink(httpServletRequest);
        return learnersService.preRegistrationByCourseId(courseId);
    }

    @DeleteMapping("/pre-registration/{preRegistrationId}")
    public ResponseEntity<Void> deletePreRegistrationByCourseId(HttpServletRequest httpServletRequest, @PathVariable String preRegistrationId) {
        authService.pink(httpServletRequest);
        return learnersService.deletePreRegistrationByCourseId(preRegistrationId);
    }

    @DeleteMapping("/{learnerId}")
    public ResponseEntity<Learners> getLearnerByLearnerId(@PathVariable("learnerId") String learnerId) {

        return learnersService.getLearnerByLearnerId(learnerId);
    }
}
