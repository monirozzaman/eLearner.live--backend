package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Learners;
import live.elearners.domain.model.PreRegistration;
import live.elearners.domain.model.RegisteredLearner;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.PaymentInfoRequest;
import live.elearners.dto.response.PaymentStepStatusResponse;
import live.elearners.dto.response.PreRegistrationResponse;
import live.elearners.dto.response.PreRegistrationWithDetailsResponse;
import live.elearners.exception.ForbiddenException;
import live.elearners.exception.ResourseNotFoundException;
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
public class LearnersService {
    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final PreRegistrationRepository preRegistrationRepository;
    private final LearnersRepository learnersRepository;

//    public ResponseEntity<Void> enrollment(LearnersEnrollmentRequest learnersEnrollmentRequest, String preRegistrationId) {
//
//        int count = 1;
//        if (authUtil.getRole().equals("LEARNER")) {
//
//            Optional<PreRegistration> preRegistrationOptional = preRegistrationRepository.findById(preRegistrationId);
//            if (!preRegistrationOptional.isPresent()) {
//                throw new ResourseNotFoundException("Pre Registration Not found");
//            } else {
//
//                PreRegistration preRegistration = preRegistrationOptional.get();
//                Optional<Course> optionalCourse = courseRepository.findById(preRegistration.getRegisteredCourseId());
//                if (!optionalCourse.isPresent()) {
//                    return new ResponseEntity(HttpStatus.NOT_FOUND);
//                }
//
//                Course course = optionalCourse.get();
//                RegisteredLearner registeredLearner = new RegisteredLearner();
//                registeredLearner.setLearnerId(authUtil.getLoggedUserId());
//                registeredLearner.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
//                registeredLearner.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
//                registeredLearner.setPaid(learnersEnrollmentRequest.getPaid());
//                registeredLearner.setPaymentVerified(false);
//                registeredLearner.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
//                if (!course.getRegisteredLearners().isEmpty()) {
//                    for (RegisteredLearner registeredLearner1 : course.getRegisteredLearners()) {
//                        if (registeredLearner1.getLearnerId().equals(authUtil.getLoggedUserId())) {
//                            System.out.println("Already Enrollment done");
//                            count++;
//                        }
//                    }
//                } else {
//                    course.getRegisteredLearners().add(registeredLearner);
//                    courseRepository.save(course);
//                    preRegistrationRepository.deleteById(preRegistrationId);
//
//                    Optional<Learners> optionalLearners = learnersRepository.findById(authUtil.getLoggedUserId());
//                    if (!optionalCourse.isPresent()) {
//                        throw new ResourseNotFoundException("Learner Not Found");
//                    }
//                    Learners learners = optionalLearners.get();
//                    RegisteredCourses registeredCourses = new RegisteredCourses();
//                    registeredCourses.setCourseId(preRegistration.getRegisteredCourseId());
//                    registeredCourses.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
//                    registeredCourses.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
//                    registeredCourses.setPaid(learnersEnrollmentRequest.getPaid());
//                    registeredCourses.setPaymentVerified(false);
//                    registeredCourses.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
//                    learners.getRegisteredCourses().add(registeredCourses);
//                    learnersRepository.save(learners);
//                    return new ResponseEntity(HttpStatus.OK);
//                }
//                if (count == 1) {
//                    course.getRegisteredLearners().add(registeredLearner);
//                    courseRepository.save(course);
//                    preRegistrationRepository.deleteById(preRegistrationId);
//
//                    Optional<Learners> optionalLearners = learnersRepository.findById(authUtil.getLoggedUserId());
//                    if (!optionalCourse.isPresent()) {
//                        throw new ResourseNotFoundException("Learner Not Found");
//                    }
//                    Learners learners = optionalLearners.get();
//                    RegisteredCourses registeredCourses = new RegisteredCourses();
//                    registeredCourses.setCourseId(preRegistration.getRegisteredCourseId());
//                    registeredCourses.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
//                    registeredCourses.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
//                    registeredCourses.setPaid(learnersEnrollmentRequest.getPaid());
//                    registeredCourses.setPaymentVerified(false);
//                    registeredCourses.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
//                    learners.getRegisteredCourses().add(registeredCourses);
//                    learnersRepository.save(learners);
//                    return new ResponseEntity(HttpStatus.OK);
//                }
//
//            }
//
//
//        } else {
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//    }

    public ResponseEntity<PreRegistrationResponse> updatePaymentStep(String courseId, String step) {

        if (authUtil.getRole().equals("LEARNER")) {


            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

            Course course = courseOptional.get();
            RegisteredLearner registeredLearner = new RegisteredLearner();
            registeredLearner.setLearnerId(authUtil.getLoggedUserId());
            registeredLearner.setEnrollmentStepNo(step);
            course.getRegisteredLearners().add(registeredLearner);
            courseRepository.save(course);
            //TODO : MUST be sent mail with full course details
            //TODO : MUST be add AUDIT CLASS

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

    public ResponseEntity<Learners> getLearnerByLearnerId(String learnerId) {

        Optional<Learners> optionalLearners = learnersRepository.findById(learnerId);
        if (!optionalLearners.isPresent()) {
            throw new ResourseNotFoundException("Learners Not found");
        }
        return new ResponseEntity(optionalLearners.get(), HttpStatus.OK);
    }

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

    public ResponseEntity<List<Learners>> getLearners() {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            List<Learners> learnersList = learnersRepository.findAll();
            return new ResponseEntity(learnersList, HttpStatus.OK);

        } else {
            throw new ForbiddenException("Access Deny");
        }
    }

    public ResponseEntity<Void> updateStepNo(String courseId, String stepNo) {
        if (authUtil.equals("LEARNER")) {
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

    public ResponseEntity<Void> updateStepStatus(String courseId, String enrollmentStepNo, String learnerId) {
        if (authUtil.equals("ADMIN") || authUtil.equals("ROLE_ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            Course course = optionalCourse.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                if (registeredLearner.getLearnerId().equals(learnerId)) {
                    registeredLearner.setEnrollmentStepStatus(enrollmentStepNo);
                }
            }
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<PaymentStepStatusResponse> getPaymentStepStatus(String courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Course Not Found");
        }
        for (RegisteredLearner registeredLearner : course.get().getRegisteredLearners()) {
            System.out.print(registeredLearner.getLearnerId() + "------------------------" + authUtil.getLoggedUserId());
            if (registeredLearner.getLearnerId().equals(authUtil.getLoggedUserId())) {
                PaymentStepStatusResponse paymentStepStatusResponse = new PaymentStepStatusResponse();
                paymentStepStatusResponse.setStep(registeredLearner.getEnrollmentStepNo());
                paymentStepStatusResponse.setPaymentStatus(registeredLearner.isPaymentVerified());
                return new ResponseEntity(paymentStepStatusResponse, HttpStatus.OK);
            }
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> updatePaymentStatus(String courseId, PaymentInfoRequest paymentInfoRequest) {
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
                    registeredLearner.setEnrollmentStepStatus("Okay");
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
}
