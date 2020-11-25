package live.elearners.controller;

import live.elearners.domain.model.ClassDocuments;
import live.elearners.dto.request.ClassDocumentRequest;
import live.elearners.services.AuthService;
import live.elearners.services.CourseDocumentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin("*")
public class CourseDocumentsController {

    private final AuthService authService;
    private final CourseDocumentsService courseDocumentsService;

    /*
     * POST Mapping
     * */
    @PostMapping("/{courseId}/classes/{classId}")
    public ResponseEntity<ClassDocuments> addClassDocuments(HttpServletRequest httpServletRequest,
                                                            @PathVariable("courseId") String courseId,
                                                            @PathVariable("classId") String classId,
                                                            @RequestBody ClassDocumentRequest classDocumentRequest) {
        authService.pink(httpServletRequest);
        return courseDocumentsService.addClassDocuments(courseId, classId, classDocumentRequest);
    }


    /*
     * GET Mapping
     * */
    @GetMapping("/{courseId}/classes/{classId}")
    public ResponseEntity<ClassDocuments> getClassDocumentsById(
            @PathVariable("courseId") String courseId,
            @PathVariable("classId") String classId) {
        return courseDocumentsService.getClassDocumentsById(courseId, classId);
    }

}
