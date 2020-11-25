package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.CourseReviewer;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.dto.request.ReviewRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseReviewService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;


    public ResponseEntity<Void> addNewReview(ReviewRequest reviewRequest, String courseId) {
        if (authUtil.getRole().equals("LEARNER") || authUtil.getRole().equals("ADMIN")) {

            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            CourseReviewer courseReviewer = new CourseReviewer();
            courseReviewer.setName(authUtil.getLoggedUserName());
            courseReviewer.setBatch(authUtil.getLoggedUserId().substring(authUtil.getLoggedUserId().length() - 2));
            courseReviewer.setReview(reviewRequest.getReview());
            courseReviewer.setStar(reviewRequest.getStar());
            course.getCourseReviewers().add(courseReviewer);
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> deleteReview(String courseId, Long reviewId) {
        if (authUtil.getRole().equals("ADMIN")) {

            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            CourseReviewer courseReviewerObject = new CourseReviewer();
            for (CourseReviewer courseReviewer : course.getCourseReviewers()) {

                if (courseReviewer.getId().equals(reviewId)) {
                    courseReviewerObject = courseReviewer;
                }
            }
            course.getCourseReviewers().remove(courseReviewerObject);
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
