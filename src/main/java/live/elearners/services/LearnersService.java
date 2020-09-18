package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.LearnersEnrollmentRequest;
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

    public ResponseEntity<Void> enrollment(LearnersEnrollmentRequest learnersEnrollmentRequest, String preRegistrationId) {

        int count = 1;
        if (authUtil.getRole().equals("LEARNER")) {

            Optional<PreRegistration> preRegistrationOptional = preRegistrationRepository.findById(preRegistrationId);
            if (!preRegistrationOptional.isPresent()) {
                throw new ResourseNotFoundException("Pre Registration Not found");
            } else {

                PreRegistration preRegistration = preRegistrationOptional.get();
                Optional<Course> optionalCourse = courseRepository.findById(preRegistration.getRegisteredCourseId());
                if (!optionalCourse.isPresent()) {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }

                Course course = optionalCourse.get();
                RegisteredLearner registeredLearner = new RegisteredLearner();
                registeredLearner.setLearnerId(authUtil.getLoggedUserId());
                registeredLearner.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
                registeredLearner.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
                registeredLearner.setPaid(learnersEnrollmentRequest.getPaid());
                registeredLearner.setPaymentVerified(false);
                registeredLearner.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
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
                    preRegistrationRepository.deleteById(preRegistrationId);

                    Optional<Learners> optionalLearners = learnersRepository.findById(authUtil.getLoggedUserId());
                    if (!optionalCourse.isPresent()) {
                        throw new ResourseNotFoundException("Learner Not Found");
                    }
                    Learners learners = optionalLearners.get();
                    RegisteredCourses registeredCourses = new RegisteredCourses();
                    registeredCourses.setCourseId(preRegistration.getRegisteredCourseId());
                    registeredCourses.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
                    registeredCourses.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
                    registeredCourses.setPaid(learnersEnrollmentRequest.getPaid());
                    registeredCourses.setPaymentVerified(false);
                    registeredCourses.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
                    learners.getRegisteredCourses().add(registeredCourses);
                    learnersRepository.save(learners);
                    return new ResponseEntity(HttpStatus.OK);
                }
                if (count == 1) {
                    course.getRegisteredLearners().add(registeredLearner);
                    courseRepository.save(course);
                    preRegistrationRepository.deleteById(preRegistrationId);

                    Optional<Learners> optionalLearners = learnersRepository.findById(authUtil.getLoggedUserId());
                    if (!optionalCourse.isPresent()) {
                        throw new ResourseNotFoundException("Learner Not Found");
                    }
                    Learners learners = optionalLearners.get();
                    RegisteredCourses registeredCourses = new RegisteredCourses();
                    registeredCourses.setCourseId(preRegistration.getRegisteredCourseId());
                    registeredCourses.setPaymentDateAndTime(authUtil.getCurrentDateAndTime());
                    registeredCourses.setPaymentMethod(learnersEnrollmentRequest.getPaymentMethod());
                    registeredCourses.setPaid(learnersEnrollmentRequest.getPaid());
                    registeredCourses.setPaymentVerified(false);
                    registeredCourses.setPaymentTrxId(learnersEnrollmentRequest.getPaymentTrxId());
                    learners.getRegisteredCourses().add(registeredCourses);
                    learnersRepository.save(learners);
                    return new ResponseEntity(HttpStatus.OK);
                }

            }


        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<PreRegistrationResponse> preRegistrationByCourseId(String courseId) {

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
            Optional<List<PreRegistration>> preRegistrationsOptional = preRegistrationRepository.findByLearnerId(authUtil.getLoggedUserEmail());

            if (!preRegistrationsOptional.isPresent()) {
                throw new ResourseNotFoundException("Pre-Registration Not found");
            }
            for (PreRegistration preRegistration : preRegistrationsOptional.get()) {
                Optional<Course> courseOptional = courseRepository.findById(preRegistration.getRegisteredCourseId());
                if (!courseOptional.isPresent()) {

                }
                PreRegistrationWithDetailsResponse preRegistrationWithDetailsResponse = new PreRegistrationWithDetailsResponse();
                preRegistrationWithDetailsResponse.setPreRegistrationId(preRegistration.getPreRegistrationId());
                preRegistrationWithDetailsResponse.setCourseId(preRegistration.getRegisteredCourseId());
                preRegistrationWithDetailsResponse.setCourseName(preRegistration.getRegisteredCourseName());
                preRegistrationWithDetailsResponse.setImageDetails(courseOptional.get().getImageDetails());
                preRegistrationWithDetailsResponse.setOrientationDateTime(preRegistration.getOrientationDateTime());
                preRegistrationWithDetailsResponses.add(preRegistrationWithDetailsResponse);
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
}
