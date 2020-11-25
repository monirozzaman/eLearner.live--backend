package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.*;
import live.elearners.dto.response.DashboardResponse;
import live.elearners.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


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
