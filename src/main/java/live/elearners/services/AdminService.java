package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.RegisteredLearner;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AdminService {
    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final InstructorsRepository instructorsRepository;
    private final PreRegistrationRepository preRegistrationRepository;

    public ResponseEntity<Void> enrollmentVerify(String courseId, String leanerId) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            RegisteredLearner registeredLearner = new RegisteredLearner();
            registeredLearner.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
            registeredLearner.setPaymentVerified(false);


            Course course = optionalCourse.get();
            if (!course.getRegisteredLearners().isEmpty()) {
                for (RegisteredLearner registeredLearner1 : course.getRegisteredLearners()) {
                    if (registeredLearner1.getLearnerId().equals(leanerId)) {
                        registeredLearner1.setPaymentVerified(true);
                        registeredLearner1.setPaymentVerifyDateAndTime(authUtil.getCurrentDateAndTime());
                        courseRepository.save(course);
                        return new ResponseEntity(HttpStatus.OK);
                    }
                }
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updatePaymentInfo(String courseId, String learnerId, LearnersEnrollmentRequest learnersEnrollmentRequest) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {

            }
            Course course = optionalCourse.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                if (registeredLearner.getLearnerId().equals(learnerId)) {
                    registeredLearner.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
                    registeredLearner.setPaid(learnersEnrollmentRequest.getPaid());
                    registeredLearner.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());

                }
            }
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }

    public ResponseEntity<Void> updateCourseActivationStatus(String courseId, String learnerId, LearnerActiveRequest learnerActiveRequest) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {

            }
            Course course = optionalCourse.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                if (registeredLearner.getLearnerId().equals(learnerId)) {
                    registeredLearner.setPaymentVerified(learnerActiveRequest.getIsActive());

                }
            }
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<List<Instructors>> getInstructors() {

        return new ResponseEntity(instructorsRepository.findAll(), HttpStatus.OK);
    }
}
