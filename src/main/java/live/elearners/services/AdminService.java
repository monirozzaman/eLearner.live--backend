package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.*;
import live.elearners.dto.request.CourseOfferAddRequest;
import live.elearners.dto.request.LearnerActiveRequest;
import live.elearners.dto.request.LearnersEnrollmentRequest;
import live.elearners.dto.response.DashboardResponse;
import live.elearners.exception.ForbiddenException;
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
    private final AdminRepository adminRepository;
    private final CourseSectionsRepository courseSectionsRepository;
    private final PreRegistrationRepository preRegistrationRepository;
    private final LearnersRepository learnersRepository;


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

    public ResponseEntity<String> addOfferInCourse(String courseId, CourseOfferAddRequest courseOfferAddRequest) {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                return new ResponseEntity("Course Not Found", HttpStatus.NOT_FOUND);
            }
            Course course = courseOptional.get();
            Offers offers = course.getOffer();
            offers.setImageId(offers.getImageId());
            offers.setBasicOfferInPercentage(courseOfferAddRequest.getOffer().getBasicOfferInPercentage());
            offers.setSpecialOfferEndDate(courseOfferAddRequest.getOffer().getSpecialOfferEndDate());
            offers.setSpecialOfferInPercentage(courseOfferAddRequest.getOffer().getSpecialOfferInPercentage());
            offers.setSpecialOfferReason(courseOfferAddRequest.getOffer().getSpecialOfferReason());
            offers.setSpecialOfferStatDate(courseOfferAddRequest.getOffer().getSpecialOfferStatDate());
            course.setOffer(offers);
            courseRepository.save(course);
            return new ResponseEntity("Offer added", HttpStatus.OK);

        } else {
            return new ResponseEntity("Access deny", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<List<Admin>> getAdminList() {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            List<Admin> admin = adminRepository.findAll();
            return new ResponseEntity(admin, HttpStatus.OK);
        } else {
            throw new ForbiddenException("Access Deny");
        }
    }

    public ResponseEntity<DashboardResponse> getDashboardDetails() {
        List<Admin> adminList = adminRepository.findAll();
        List<CourseSections> courseSections = courseSectionsRepository.findAll();
        List<Instructors> instructorsList = instructorsRepository.findAll();
        List<Course> courseList = courseRepository.findAll();
        List<Learners> learnersList = learnersRepository.findAll();
        List<PreRegistration> preRegistrationList = preRegistrationRepository.findAll();

        DashboardResponse dashboardResponse = new DashboardResponse();
        dashboardResponse.setNumberOfAdmins(adminList.size());
        dashboardResponse.setNumberOfCategory(courseSections.size());
        dashboardResponse.setNumberOfCategoryInstructors(instructorsList.size());
        dashboardResponse.setNumberOfCourses(courseList.size());
        dashboardResponse.setNumberOfEngineers(0);
        dashboardResponse.setNumberOfLearners(learnersList.size() + preRegistrationList.size());
        return new ResponseEntity(dashboardResponse, HttpStatus.OK);

    }
}
