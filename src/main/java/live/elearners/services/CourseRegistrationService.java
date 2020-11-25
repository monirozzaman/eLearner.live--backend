package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.dto.response.PreRegistrationWithDetailsResponse;
import live.elearners.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseRegistrationService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final PreRegistrationRepository preRegistrationRepository;
    private final LearnersRepository learnersRepository;

    /*
     * Get Pre-Registration Courses By Course Id.
     * */
    public ResponseEntity<List<PreRegistrationWithDetailsResponse>> getPreRegistrationCourses() {
        if (authUtil.getRole().equals("LEARNER")) {

            List<PreRegistrationWithDetailsResponse> preRegistrationWithDetailsResponses = new ArrayList<>();
            List<Course> courseList = courseRepository.findAll();
            for (Course course : courseList) {
                for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                    if (registeredLearner.getLearnerId().equals(authUtil.getLoggedUserId())) {

                        PreRegistrationWithDetailsResponse preRegistrationWithDetailsResponse = new PreRegistrationWithDetailsResponse();
                        preRegistrationWithDetailsResponse.setPreRegistrationId(registeredLearner.getLearnerId());
                        preRegistrationWithDetailsResponse.setCourseId(course.getCourseId());
                        preRegistrationWithDetailsResponse.setCourseName(course.getCourseName());
                        preRegistrationWithDetailsResponse.setImageDetails(course.getImageDetails());
                        preRegistrationWithDetailsResponse.setOrientationDateTime(course.getCourseOrientationDate());
                        preRegistrationWithDetailsResponses.add(preRegistrationWithDetailsResponse);
                    }
                }
            }
            return new ResponseEntity(preRegistrationWithDetailsResponses, HttpStatus.OK);
        } else {
            throw new ForbiddenException("Access Deny for this role");
        }
    }

    public ResponseEntity<PreRegistrationResponse> preRegistrationInACourseByCourseId(String courseId) {

        if (authUtil.getRole().equals("LEARNER")) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            Course course = courseOptional.get();
            RegisteredLearner registeredLearner = new RegisteredLearner();
            registeredLearner.setLearnerId(authUtil.getLoggedUserId());
            registeredLearner.setEnrollmentStepNo("1");
            for (RegisteredLearner registeredLearnerForServer : course.getRegisteredLearners()) {
                if (registeredLearnerForServer.getLearnerId().equals(authUtil.getLoggedUserId())) {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Already Registered This Course");
                }
            }
            course.getRegisteredLearners().add(registeredLearner);
            courseRepository.save(course);
            //TODO : MUST be sent mail with full course details
            //TODO : MUST be add AUDIT CLASS

            //Save Registered Course Info In Learners Profile
            Optional<Learners> learnersOptional = learnersRepository.findById(authUtil.getLoggedUserId());
            if (!learnersOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Learner Account Not Found");
            }
            Learners learners = learnersOptional.get();
            RegisteredCourses registeredCourses = new RegisteredCourses();
            registeredCourses.setCourse(course);
            learners.getRegisteredCourses().add(registeredCourses);
            learnersRepository.save(learners);

            return new ResponseEntity(new PreRegistrationResponse(course.getCourseId(), course.getCourseOrientationDate()), HttpStatus.OK);
        } else {
            return new ResponseEntity(new PreRegistrationResponse("null", "null"), HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> deletePreRegistrationByCourseId(String preRegistrationId) {
        Optional<PreRegistration> preRegistrationOptional = preRegistrationRepository.findById(preRegistrationId);
        if (!preRegistrationOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        preRegistrationRepository.deleteById(preRegistrationId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
