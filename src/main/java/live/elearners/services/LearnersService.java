package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Learners;
import live.elearners.domain.model.RegisteredCourses;
import live.elearners.domain.model.RegisteredLearner;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.domain.repository.PreRegistrationRepository;
import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.response.LearnerResponse;
import live.elearners.dto.response.RegisteredCourseResponse;
import live.elearners.exception.ForbiddenException;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Void> learnerActivation(String courseId, String learnerId, LearnerActiveRequest learnerActiveRequest) {
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

}
