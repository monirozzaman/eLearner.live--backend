package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;

    public ResponseEntity<CourseIdentityResponse> createCourse(CourseRequest courseRequest) {

        String uuid = authUtil.getRandomUUID();
        if (authUtil.getRole().equals("ADMIN")) {
            Course course = new Course();
            course.setCourseId(uuid);
            course.setCreateBy(authUtil.getEmployeeId());
            course.setIsPublish(false);
            course.setCourseGoal(courseRequest.getCourseGoal());
            course.setCourseMaxNumberOfLearner(courseRequest.getCourseMaxNumberOfLearner());
            course.setCourseOrientationDate(courseRequest.getCourseOrientationDate());
            course.setCourseStartingDate(courseRequest.getCourseStartingDate());
            course.setCourseFinishingDate(courseRequest.getCourseFinishingDate());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
            course.setCourseNumberOfClasses(courseRequest.getCourseNumberOfClasses());
            course.setCourseClassDuration(courseRequest.getCourseClassDuration());
            course.setCourseClassTimeSchedule(courseRequest.getCourseClassTimeSchedule());
            course.setCourseInstructorId(courseRequest.getCourseInstructorId());
            course.setCourseInstructorName(courseRequest.getCourseInstructorName());
            course.setCourseInstructorPhoneNumber(courseRequest.getCourseInstructorPhoneNumber());
            course.setCoursePriceInTk(courseRequest.getCoursePriceInTk());
            course.setCoursePriceInOffer(courseRequest.getCoursePriceInOffer());
            courseRepository.save(course);
        } else {
            return new ResponseEntity(new CourseIdentityResponse("Access Deny"), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(new CourseIdentityResponse(uuid), HttpStatus.OK);
    }
}
