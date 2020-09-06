package live.elearners.controller;

import com.google.gson.Gson;
import live.elearners.domain.model.Course;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin("*")
public class CoursesController {
    private final AuthService authService;
    private final CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseIdentityResponse> createCourse(HttpServletRequest httpServletRequest,
                                                               @RequestParam("courseRequestInString") String courseRequestInString,
                                                               @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        CourseRequest courseRequest = g.fromJson(courseRequestInString, CourseRequest.class);
        return courseService.createCourse(courseRequest, file);
    }

    @GetMapping("")
    public ResponseEntity<CourseResponse> getCourses(@PageableDefault(size = 5) Pageable pageable) {

        return courseService.getCourse(pageable);
    }

    @GetMapping("/sections/{courseSectionId}")
    public ResponseEntity<List<Course>> getCourseBySectionId(@PathVariable String courseSectionId) {

        return courseService.getCourseBySectionId(courseSectionId);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId) {

        return courseService.getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public void updateCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                 @RequestParam("courseRequestInString") String courseRequestInString,
                                 @RequestParam("file") MultipartFile file) {

        authService.pink(httpServletRequest);
        Gson g = new Gson();
        CourseRequest courseRequest = g.fromJson(courseRequestInString, CourseRequest.class);
        courseService.updateCourseById(courseId, courseRequest, file);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId) {

        authService.pink(httpServletRequest);
        return courseService.deleteCourseById(courseId);
    }

    @PutMapping("/{courseId}/publish")
    public ResponseEntity<Void> updateCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId, @RequestBody CoursePublishRequest coursePublishRequest) {

        authService.pink(httpServletRequest);
        return courseService.coursePublishByCourseId(courseId, coursePublishRequest);
    }

    //DEMO
    @GetMapping(value = "/{courseId}/download/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadFile(@PathVariable String courseId, HttpServletResponse response, HttpServletRequest request) {
        return courseService.downloadUrl(courseId, response, request);
    }


}
