package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.PaymentInfoRequest;
import live.elearners.dto.response.*;
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
            for(RegisteredLearner registeredLearnerForServer : course.getRegisteredLearners())
            {
                if(registeredLearnerForServer.getLearnerId().equals(authUtil.getLoggedUserId()))
                {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Already Registered This Course");
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

    /*
     * Get Learner Courses By Learner Id.
     * */
    public ResponseEntity<Learners> getLearnerByLearnerId(String learnerId) {

        Optional<Learners> optionalLearners = learnersRepository.findById(learnerId);
        if (!optionalLearners.isPresent()) {
            throw new ResourseNotFoundException("Learners Not found");
        }
        return new ResponseEntity(optionalLearners.get(), HttpStatus.OK);
    }

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

    /*
     * Get List of Learners
     * */
    public ResponseEntity<List<LearnerResponse>> getLearners() {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            List<LearnerResponse> learnerResponseList = new ArrayList<>();
            List<RegisteredCourseResponse> registeredCourseResponses = new ArrayList<>();
            List<Learners> learnersList = learnersRepository.findAll();
            for (Learners learner : learnersList) {
                LearnerResponse learnerResponse = new LearnerResponse();
                learnerResponse.setAuthId(learner.getAuthId());
                learnerResponse.setCurrentAddress(learner.getCurrentAddress());
                learnerResponse.setEmail(learner.getEmail());
                learnerResponse.setIsActive(learner.getIsActive());
                learnerResponse.setIsEmailVerified(learner.getIsEmailVerified());
                learnerResponse.setLearnerId(learner.getLearnerId());
                learnerResponse.setName(learner.getName());
                learnerResponse.setPaymentStep(learner.getPaymentStep());
                learnerResponse.setPhoneNo(learner.getPhoneNo());
                learnerResponse.setPresentWorkField(learner.getPresentWorkField());
                learnerResponse.setCurrentAddress(learner.getCurrentAddress());

                for (RegisteredCourses registeredCourses : learner.getRegisteredCourses()) {
                    Course course = registeredCourses.getCourse();

                    for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                        if (registeredLearner.getLearnerId().equals(learner.getLearnerId())) {
                            RegisteredCourseResponse registeredCourseResponse = new RegisteredCourseResponse();
                            registeredCourseResponse.setCourseName(course.getCourseName());
                            registeredCourseResponse.setCommitmentDuePaidDate(registeredLearner.getCommitmentDuePaidDate());
                            registeredCourseResponse.setDue(registeredLearner.getDue());
                            registeredCourseResponse.setEnrollmentStepNo(registeredLearner.getEnrollmentStepNo());
                            registeredCourseResponse.setPaid(registeredLearner.getPaid());
                            registeredCourseResponse.setPaymentDateAndTime(registeredLearner.getPaymentDateAndTime());
                            registeredCourseResponse.setPaymentMethod(registeredLearner.getPaymentMethod());
                            registeredCourseResponse.setPaymentTrxId(registeredLearner.getPaymentTrxId());
                            registeredCourseResponse.setPaymentVerified(registeredLearner.isPaymentVerified());
                            registeredCourseResponse.setPaymentVerifyDateAndTime(registeredLearner.getPaymentVerifyDateAndTime());
                            registeredCourseResponses.add(registeredCourseResponse);

                        }
                    }
                    learnerResponse.setRegisteredCourseResponses(registeredCourseResponses);

                }
                learnerResponseList.add(learnerResponse);
            }
            return new ResponseEntity(learnerResponseList, HttpStatus.OK);
        } else {
            throw new ForbiddenException("Access Deny");
        }
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
}
