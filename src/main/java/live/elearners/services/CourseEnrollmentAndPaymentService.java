package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.RegisteredLearner;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.request.PaymentInfoRequest;
import live.elearners.dto.response.PaymentStepStatusResponse;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseEnrollmentAndPaymentService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;

    /*
     * Get Enrollment No.
     * */
    public ResponseEntity<PaymentStepStatusResponse> getEnrollmentStepStatus(String courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Course Not Found");
        }
        for (RegisteredLearner registeredLearner : course.get().getRegisteredLearners()) {
            if (registeredLearner.getLearnerId().equals(authUtil.getLoggedUserId())) {
                PaymentStepStatusResponse paymentStepStatusResponse = new PaymentStepStatusResponse();
                paymentStepStatusResponse.setStep(registeredLearner.getEnrollmentStepNo());
                paymentStepStatusResponse.setPaymentStatus(registeredLearner.isPaymentVerified());
                return new ResponseEntity(paymentStepStatusResponse, HttpStatus.OK);
            }
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    /*
     * Update Enrollment Step No.
     * */
    public ResponseEntity<Void> updateEnrollmentStepNo(String courseId, String stepNo) {
        if (authUtil.getRole().equals("LEARNER")) {

            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            Course course = optionalCourse.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                if (registeredLearner.getLearnerId().equals(authUtil.getLoggedUserId())) {
                    registeredLearner.setEnrollmentStepNo(stepNo);
                }
            }
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }

    /*
     * Update Payment Status By Course Id for Logged User
     * */
    public ResponseEntity<Void> updatePaymentStatusByCourseId(String courseId, PaymentInfoRequest paymentInfoRequest) {
        if (authUtil.getRole().equals("LEARNER")) {

            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            Course course = courseOptional.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {

                if (registeredLearner.getLearnerId().equals(authUtil.getLoggedUserId())) {
                    registeredLearner.setEnrollmentStepNo("2");
                    registeredLearner.setCommitmentDuePaidDate("Not Applicable");
                    registeredLearner.setDue("00");
                    registeredLearner.setPaid(paymentInfoRequest.getPaid());
                    registeredLearner.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
                    registeredLearner.setPaymentTrxId(paymentInfoRequest.getPaymentTrxId());
                    registeredLearner.setPaymentMethod(paymentInfoRequest.getPaymentMethod());
                    registeredLearner.setPaymentVerified(false);
                }
            }
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    /*
     * Verify enrollment Request via check Payment Info
     * */
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

    /*
     * Update Payment Info
     * */
    public ResponseEntity<Void> updatePaymentInfo(String courseId, String learnerId, LearnersEnrollmentRequest learnersEnrollmentRequest) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                throw new ResourseNotFoundException("Course Not Found");
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
}
