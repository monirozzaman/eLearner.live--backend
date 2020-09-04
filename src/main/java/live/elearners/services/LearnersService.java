package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.PreRegistration;
import live.elearners.domain.model.RegisteredLearner;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.request.PreRegistrationRequest;
import live.elearners.dto.response.PreRegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class LearnersService {
    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final PreRegistrationRepository preRegistrationRepository;

    public ResponseEntity<Void> enrollment(String courseId, LearnersEnrollmentRequest learnersEnrollmentRequest) {
        int count = 1;
        if (authUtil.getRole().equals("LEARNER")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            RegisteredLearner registeredLearner = new RegisteredLearner();
            registeredLearner.setLearnerId(authUtil.getLoggedUserId());
            registeredLearner.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
            registeredLearner.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
            registeredLearner.setPaid(learnersEnrollmentRequest.getPaid());
            registeredLearner.setPaymentVerified(false);
            registeredLearner.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());


            Course course = optionalCourse.get();
            if (!course.getRegisteredLearners().isEmpty()) {
                for (RegisteredLearner registeredLearner1 : course.getRegisteredLearners()) {
                    if (registeredLearner1.getLearnerId().equals(authUtil.getLoggedUserId())) {
                        System.out.println("Already Enrollment done");
                        count++;
                    }
                }
            } else {
                course.getRegisteredLearners().add(registeredLearner);
                courseRepository.save(course);
                return new ResponseEntity(HttpStatus.OK);
            }
            if (count == 1) {
                course.getRegisteredLearners().add(registeredLearner);
                courseRepository.save(course);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<PreRegistrationResponse> preRegistrationByCourseId(String courseId, PreRegistrationRequest preRegistrationRequest) {

        if (authUtil.getRole().equals("LEARNER")) {

            String uuid = authUtil.getRandomUUID();
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            Course course = courseOptional.get();
            PreRegistration preRegistration = new PreRegistration();
            preRegistration.setName(authUtil.getLoggedUserName());
            preRegistration.setRegisteredDateAndTime(authUtil.getCurrentDateAndTime());
            preRegistration.setAddress(authUtil.getLoggedUserAddress());
            preRegistration.setEmail(authUtil.getLoggedUserEmail());
            preRegistration.setInterestLevel(preRegistrationRequest.getInterestLevel());
            preRegistration.setPhoneNo(authUtil.getLoggedUserPhoneNumber());
            preRegistration.setPreRegistrationId(uuid);
            preRegistration.setOrientationDateTime(course.getCourseOrientationDate());
            preRegistration.setRegisteredCourseId(courseId);
            preRegistration.setRegisteredCourseName(course.getCourseName());
            preRegistration.setRegisteredCourseSectionId(course.getCourseSectionId());

            preRegistrationRepository.save(preRegistration);
            //TODO : MUST be sent mail with full course details
            //TODO : MUST be add AUDIT CLASS

            return new ResponseEntity(new PreRegistrationResponse(uuid, course.getCourseOrientationDate()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new PreRegistrationResponse("null", "null"), HttpStatus.FORBIDDEN);
        }
    }

}
