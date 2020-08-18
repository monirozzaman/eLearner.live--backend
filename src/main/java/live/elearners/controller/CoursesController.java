package live.elearners.controller;

import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("courses")
public class CoursesController {
    private final AuthService authService;
    private final CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseIdentityResponse> createCourse(HttpServletRequest httpServletRequest,
                                                               @RequestBody CourseRequest courseRequest) {
        authService.pink(httpServletRequest);

        return courseService.createCourse(courseRequest);
    }
}
