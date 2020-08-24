package live.elearners.services;

import jdk.management.resource.ResourceRequestDeniedException;
import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.request.CourseUpdateRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseResponse;
import live.elearners.exception.ForbiddenException;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
            course.setCreateBy(authUtil.getLoggedUserId());
            course.setIsPublish(false);
            course.setCourseGoal(courseRequest.getCourseGoal());
            course.setCourseType(courseRequest.getCourseType());
            course.setCourseName(courseRequest.getCourseName());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
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
            course.setCourseInstructorQualification(courseRequest.getCourseInstructorQualification());
            course.setCoursePriceInTk(courseRequest.getCoursePriceInTk());
            course.setCoursePriceInOffer(courseRequest.getCoursePriceInOffer());
            courseRepository.save(course);
        } else {
            throw new ForbiddenException("Access Deny");
        }

        return new ResponseEntity(new CourseIdentityResponse(uuid), HttpStatus.OK);
    }

    public ResponseEntity<CourseResponse> getCourse(Pageable pageable) {
       Page<Course> coursesPageable = courseRepository.findAll(pageable);
       CourseResponse courseResponse= new CourseResponse();
       courseResponse.setPage(coursesPageable.getNumber());
       courseResponse.setSize(coursesPageable.getSize());
       courseResponse.setTotalElements(coursesPageable.getTotalElements());
       courseResponse.setTotalPages(coursesPageable.getTotalPages());
        List<Course> courses = courseRepository.findAll();

        courseResponse.setItems(courses);
        return new ResponseEntity(courseResponse,HttpStatus.OK);

    }

    public ResponseEntity<Course> getCourseById(String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            return new ResponseEntity(optionalCourse.get(), HttpStatus.OK);
        } else {
            throw new ResourceRequestDeniedException("Can not find any course via course id");
        }
    }

    public void updateCourseById(String courseId, CourseUpdateRequest courseUpdateRequest) {

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if(authUtil.getRole().equals("ADMIN")) {

                course.setCreateBy(authUtil.getLoggedUserId());
                course.setIsPublish(false);
                course.setCourseGoal(courseUpdateRequest.getCourseGoal());
                course.setCourseMaxNumberOfLearner(courseUpdateRequest.getCourseMaxNumberOfLearner());
                course.setCourseOrientationDate(courseUpdateRequest.getCourseOrientationDate());
                course.setCourseStartingDate(courseUpdateRequest.getCourseStartingDate());
                course.setCourseFinishingDate(courseUpdateRequest.getCourseFinishingDate());
                course.setCourseTotalDurationInDays(courseUpdateRequest.getCourseTotalDurationInDays());
                course.setCourseNumberOfClasses(courseUpdateRequest.getCourseNumberOfClasses());
                course.setCourseClassDuration(courseUpdateRequest.getCourseClassDuration());
                course.setCourseClassTimeSchedule(courseUpdateRequest.getCourseClassTimeSchedule());
                course.setCourseInstructorId(courseUpdateRequest.getCourseInstructorId());
                course.setCourseInstructorName(courseUpdateRequest.getCourseInstructorName());
                course.setCourseInstructorPhoneNumber(courseUpdateRequest.getCourseInstructorPhoneNumber());
                course.setCoursePriceInTk(courseUpdateRequest.getCoursePriceInTk());
                course.setCoursePriceInOffer(courseUpdateRequest.getCoursePriceInOffer());
                courseRepository.save(course);
            }
            else if(authUtil.getRole().equals("INSTRUCTOR")) {

                course.setCreateBy(authUtil.getLoggedUserId());
                course.setIsPublish(course.getIsPublish());
                course.setCourseGoal(courseUpdateRequest.getCourseGoal());
                course.setCourseMaxNumberOfLearner(courseUpdateRequest.getCourseMaxNumberOfLearner());
                course.setCourseOrientationDate(courseUpdateRequest.getCourseOrientationDate());
                course.setCourseStartingDate(courseUpdateRequest.getCourseStartingDate());
                course.setCourseFinishingDate(courseUpdateRequest.getCourseFinishingDate());
                course.setCourseTotalDurationInDays(courseUpdateRequest.getCourseTotalDurationInDays());
                course.setCourseNumberOfClasses(courseUpdateRequest.getCourseNumberOfClasses());
                course.setCourseClassDuration(courseUpdateRequest.getCourseClassDuration());
                course.setCourseClassTimeSchedule(courseUpdateRequest.getCourseClassTimeSchedule());
                course.setCourseInstructorId(authUtil.getLoggedUserId());
                course.setCourseInstructorName(course.getCourseInstructorName());
                course.setCourseInstructorPhoneNumber(course.getCourseInstructorPhoneNumber());
                course.setCoursePriceInTk(course.getCoursePriceInTk());
                course.setCoursePriceInOffer(course.getCoursePriceInOffer());
                courseRepository.save(course);
            }

        } else {
            throw new ResourceRequestDeniedException("Can not find any course via course id");
        }
    }

    public ResponseEntity<Void> coursePublishByCourseId(String courseId, CoursePublishRequest coursePublishRequest) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                course.setIsPublish(coursePublishRequest.getIsPublish());
                courseRepository.save(course);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new ResourseNotFoundException("Course not found");
            }

        } else {
            throw new ForbiddenException("Access Deny");

        }

    }
}
