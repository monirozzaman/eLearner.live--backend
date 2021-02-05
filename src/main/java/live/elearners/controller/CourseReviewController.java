package live.elearners.controller;

import live.elearners.dto.request.ReviewRequest;
import live.elearners.services.AuthService;
import live.elearners.services.CourseReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("/courses")
@CrossOrigin("*")
public class CourseReviewController {
    private final AuthService authService;
    private final CourseReviewService courseReviewService;

    /*
     * PUT Mapping
     * */
    @PutMapping("/{courseId}/review")
    public ResponseEntity<Void> addNewReview(HttpServletRequest request, @RequestBody ReviewRequest reviewRequest,
                                             @PathVariable String courseId) {
        authService.pink(request);
        return courseReviewService.addNewReview(reviewRequest, courseId);
    }

    /*
     * Delete Mapping
     * */
    @DeleteMapping("/{courseId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(HttpServletRequest request,
                                             @PathVariable String courseId, @PathVariable Long reviewId) {
        authService.pink(request);
        return courseReviewService.deleteReview(courseId, reviewId);
    }

}
