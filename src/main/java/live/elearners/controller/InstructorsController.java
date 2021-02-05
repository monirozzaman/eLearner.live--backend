package live.elearners.controller;

import com.google.gson.Gson;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Instructors;
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
@RequestMapping("/instructors")
@CrossOrigin("*")
public class InstructorsController {

    private final AuthService authService;
    private final InstructorsService instructorsService;

    /*
     * GET Mapping
     * */
    @GetMapping("/{instructorId}")
    public ResponseEntity<Instructors> getInstructorById(HttpServletRequest httpServletRequest,
                                                         @PathVariable("instructorId") String instructorId) {
        authService.pink(httpServletRequest);
        return instructorsService.getInstructorById(instructorId);
    }

    @GetMapping("")
    public ResponseEntity<List<Instructors>> getInstructorsList() {

        return instructorsService.getInstructors();
    }

    @GetMapping("/my/courses")
    public ResponseEntity<List<Course>> getCourseByInstructorId(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return instructorsService.getCourseByInstructorId();
    }

    /*
     * PUT Mapping
     * */
    @PutMapping("/logged/update")
    public ResponseEntity<Instructors> updateInstructorById(HttpServletRequest httpServletRequest,
                                                            @RequestParam("signUpInstructorRequestInString")
                                                                    String signUpInstructorRequestInString,
                                                            @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        InstructorUpdateRequest signUpInstructorRequest = g.fromJson(signUpInstructorRequestInString,
                InstructorUpdateRequest.class);
        return instructorsService.updateInstructorById(signUpInstructorRequest, file);
    }
}
