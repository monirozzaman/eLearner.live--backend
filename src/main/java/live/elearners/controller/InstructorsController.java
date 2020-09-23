package live.elearners.controller;

import com.google.gson.Gson;
import live.elearners.domain.model.ClassDocuments;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Instructors;
import live.elearners.dto.request.ClassDocumentRequest;
import live.elearners.dto.request.InstructorUpdateRequest;
import live.elearners.services.AuthService;
import live.elearners.services.InstructorsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("instructors")
@CrossOrigin("*")
public class InstructorsController {
    private final AuthService authService;
    private final InstructorsService instructorsService;

    @GetMapping("/{instructorId}")
    public ResponseEntity<Instructors> getInstructorById(HttpServletRequest httpServletRequest, @PathVariable("instructorId") String instructorId) {
        authService.pink(httpServletRequest);
        return instructorsService.getInstructorById(instructorId);
    }

    @PutMapping("")
    public ResponseEntity<Instructors> updateInstructorById(HttpServletRequest httpServletRequest, @RequestParam("signUpInstructorRequestInString") String signUpInstructorRequestInString,
                                                            @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        InstructorUpdateRequest signUpInstructorRequest = g.fromJson(signUpInstructorRequestInString, InstructorUpdateRequest.class);
        return instructorsService.updateInstructorById(signUpInstructorRequest, file);
    }

    @PostMapping("courses/{courseId}/classes/{classId}")
    public ResponseEntity<ClassDocuments> addClassDocuments(HttpServletRequest httpServletRequest,
                                                            @PathVariable("courseId") String courseId,
                                                            @PathVariable("classId") String classId, @RequestBody ClassDocumentRequest classDocumentRequest) {
        authService.pink(httpServletRequest);
        return instructorsService.addClassDocuments(courseId, classId, classDocumentRequest);
    }

    @PutMapping("courses/{courseId}/classes/{classId}")
    public ResponseEntity<ClassDocuments> updateClassDocuments(HttpServletRequest httpServletRequest,
                                                               @PathVariable("courseId") String courseId,
                                                               @PathVariable("classId") String classId, @RequestBody ClassDocumentRequest classDocumentRequest) {
        authService.pink(httpServletRequest);
        return instructorsService.addClassDocuments(courseId, classId, classDocumentRequest);
    }

    @GetMapping("courses/{courseId}/classes/{classId}")
    public ResponseEntity<ClassDocuments> getClassDocumentsById(
            @PathVariable("courseId") String courseId,
            @PathVariable("classId") String classId) {

        return instructorsService.getClassDocumentsById(courseId, classId);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourseByInstructorId(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return instructorsService.getCourseByInstructorId();
    }
}
