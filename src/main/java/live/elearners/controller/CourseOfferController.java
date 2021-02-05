package live.elearners.controller;

import live.elearners.dto.request.CourseOfferAddRequest;
import live.elearners.services.AuthService;
import live.elearners.services.CourseOfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@CrossOrigin("*")
public class CourseOfferController {

    private final AuthService authService;
    private final CourseOfferService courseOfferService;

    /*
     * PUT Mapping
     * */
    @PutMapping("/courses/{courseId}/offer")
    public ResponseEntity<String> addOffer(HttpServletRequest httpServletRequest,
                                           @PathVariable("courseId") String courseId,
                                           @RequestBody CourseOfferAddRequest courseOfferAddRequest) {
        authService.pink(httpServletRequest);
        return courseOfferService.addOfferInCourse(courseId, courseOfferAddRequest);
    }


}
