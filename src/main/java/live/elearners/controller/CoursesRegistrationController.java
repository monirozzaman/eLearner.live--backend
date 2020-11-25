package live.elearners.controller;

import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.dto.response.PreRegistrationWithDetailsResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api")
@CrossOrigin("*")
public class CoursesRegistrationController {

    private final AuthService authService;
    private final CourseRegistrationService courseRegistrationService;

    /*
     * GET Mapping
     * */
    @GetMapping("/pre-registration/courses")
    public ResponseEntity<List<PreRegistrationWithDetailsResponse>> getPreRegistrationCourses(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return courseRegistrationService.getPreRegistrationCourses();
    }


    /*
     * PUT Mapping
     * */
    @PutMapping("/courses/{courseId}/pre-registration")
    public ResponseEntity<PreRegistrationResponse> preRegistrationInACourseByCourseId(HttpServletRequest httpServletRequest,
                                                                                      @PathVariable String courseId) {
        authService.pink(httpServletRequest);
        return courseRegistrationService.preRegistrationInACourseByCourseId(courseId);
    }


    /*
     * DELETE Mapping
     * */
    @DeleteMapping("/pre-registration/{preRegistrationId}")
    public ResponseEntity<Void> deletePreRegistrationByCourseId(HttpServletRequest httpServletRequest,
                                                                @PathVariable String preRegistrationId) {
        authService.pink(httpServletRequest);
        return courseRegistrationService.deletePreRegistrationByCourseId(preRegistrationId);
    }


}
