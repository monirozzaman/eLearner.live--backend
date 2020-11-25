package live.elearners.controller;

import live.elearners.domain.model.Learners;
import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.response.LearnerResponse;
import live.elearners.services.AuthService;
import live.elearners.services.LearnersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("learners")
@CrossOrigin("*")
public class LearnersController {

    private final AuthService authService;
    private final LearnersService learnersService;

    /*
     * GET Mapping
     * */
    @GetMapping("")
    public ResponseEntity<List<LearnerResponse>> getLearners(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return learnersService.getLearners();
    }

    @GetMapping("/{learnerId}")
    public ResponseEntity<Learners> getLearnerByLearnerId(@PathVariable("learnerId") String learnerId) {
        return learnersService.getLearnerByLearnerId(learnerId);
    }

    /*
     * PUT Mapping
     * */
    @PutMapping("/courses/{courseId}/learners/{learnerId}/activation")
    public ResponseEntity<Void> updateCourseActivationStatus(HttpServletRequest httpServletRequest, @PathVariable String courseId,
                                                             @PathVariable String learnerId, @RequestBody LearnerActiveRequest learnerActiveRequest) {
        authService.pink(httpServletRequest);
        return learnersService.learnerActivation(courseId, learnerId, learnerActiveRequest);
    }

}
