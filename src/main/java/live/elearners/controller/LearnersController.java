package live.elearners.controller;

import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.request.PreRegistrationRequest;
import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.services.AuthService;
import live.elearners.services.LearnersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("courses")
public class LearnersController {
    private final AuthService authService;
    private final LearnersService learnersService;

    @PutMapping("/{courseId}/enrollment")
    public ResponseEntity<Void> enrollment(HttpServletRequest httpServletRequest, @PathVariable String courseId, @RequestBody LearnersEnrollmentRequest learnersEnrollmentRequest) {
        authService.pink(httpServletRequest);
        return learnersService.enrollment(courseId, learnersEnrollmentRequest);
    }

    @PostMapping("/{courseId}/pre-registration")
    public ResponseEntity<PreRegistrationResponse> updateCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId, @RequestBody PreRegistrationRequest preRegistrationRequest) {
        authService.pink(httpServletRequest);
        return learnersService.preRegistrationByCourseId(courseId, preRegistrationRequest);
    }
}
