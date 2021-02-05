package live.elearners.controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import live.elearners.domain.model.Learners;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseItemsResponse;
import live.elearners.dto.response.CourseResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/courses")
@CrossOrigin("*")
public class CoursesController {

    private final AuthService authService;
    private final CourseService courseService;

    /*
     * POST Mapping
     * */
    @PostMapping("")
    public ResponseEntity<CourseIdentityResponse> createCourse(HttpServletRequest httpServletRequest,
                                                               @RequestParam("courseRequestInString")
                                                                       String courseRequestInString,
                                                               @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new StringReader(courseRequestInString));
        reader.setLenient(true);
        CourseRequest courseRequest = g.fromJson(reader, CourseRequest.class);
        return courseService.createCourse(courseRequest, file);
    }


    /*
     * GET Mapping
     * */
    @GetMapping("/published")
    public ResponseEntity<CourseResponse> getPublishedCourses(@PageableDefault(size = 5) Pageable pageable) {
        return courseService.getPublishedCourses(pageable);
    }

    @GetMapping("")
    public ResponseEntity<CourseResponse> getCourses(@PageableDefault(size = 5) Pageable pageable) {
        return courseService.getCourse(pageable);
    }

    @GetMapping("/sections/{courseSectionId}")
    public ResponseEntity<List<CourseResponse>> getCourseBySectionId(@PathVariable String courseSectionId,
                                                                     @PageableDefault(size = 5) Pageable pageable) {
        return courseService.getCourseBySectionId(courseSectionId, pageable);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseItemsResponse> getCourseById(@PathVariable String courseId) {
        return courseService.getCourseById(courseId);
    }

    @GetMapping("/{courseId}/learners")
    public ResponseEntity<List<Learners>> getLearnersInCourse(@PathVariable String courseId,
                                                              HttpServletRequest request) {
        authService.pink(request);
        return courseService.getLearnersInCourse(courseId);
    }


    /*
     * PUT Mapping
     * */
    @PutMapping("/{courseId}")
    public void updateCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                 @RequestParam("courseRequestInString") String courseRequestInString,
                                 @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        CourseRequest courseRequest = g.fromJson(courseRequestInString, CourseRequest.class);
        courseService.updateCourseById(courseId, courseRequest, file);
    }

    @PutMapping("/{courseId}/publish")
    public ResponseEntity<Void> publishOrHideCourseById(HttpServletRequest httpServletRequest,
                                                        @PathVariable String courseId,
                                                        @RequestBody CoursePublishRequest coursePublishRequest) {
        authService.pink(httpServletRequest);
        return courseService.coursePublishByCourseId(courseId, coursePublishRequest);
    }


    /*
     * DELETE Mapping
     * */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseById(HttpServletRequest httpServletRequest, @PathVariable String courseId) {

        authService.pink(httpServletRequest);
        return courseService.deleteCourseById(courseId);
    }

}
