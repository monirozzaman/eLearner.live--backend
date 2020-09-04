package live.elearners.services;


import live.elearners.client.UaaClientService;
import live.elearners.client.dto.request.LoginClientRequest;
import live.elearners.client.dto.request.SignUpRequest;
import live.elearners.client.dto.response.AccessTokenResponse;
import live.elearners.client.dto.response.LoggedUserDetailsResponse;
import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Admin;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.Learners;
import live.elearners.domain.repository.AdminRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.LoginRequest;
import live.elearners.dto.request.SignUpAdminRequest;
import live.elearners.dto.request.SignUpInstructorRequest;
import live.elearners.dto.request.SignUpLearnerRequest;
import live.elearners.dto.response.IdentityResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final UaaClientService uaaClientService;
    private final LearnersRepository learnersRepository;
    private final InstructorsRepository instructorsRepository;
    private final AdminRepository adminRepository;
    private final AuthUtil authUtil;


    public ResponseEntity<AccessTokenResponse> login(LoginRequest loginRequest) {
        Optional<AccessTokenResponse> accessTokenResponseOptional = uaaClientService.login(
                new LoginClientRequest(loginRequest.getPhoneNo(), loginRequest.getPassword()));
        if (!accessTokenResponseOptional.isPresent()) {

        }
        activeLoggedUser("Bearer " + accessTokenResponseOptional.get().getToken());
        AccessTokenResponse tokenResponse = new AccessTokenResponse(accessTokenResponseOptional.get().getToken());
        System.out.println("Logged Id: " + getTest());
        return new ResponseEntity(tokenResponse, HttpStatus.OK);
    }

    public ResponseEntity<IdentityResponse> signUpForLearner(SignUpLearnerRequest signUpLearnerRequest) {
        Set<String> roles = new HashSet<>();
        String getCurrentDate = authUtil.getCurrentDate().replaceAll("/", "");
        String learnerId = getCurrentDate + authUtil.getRandomIntNumber();
        roles.add("LEARNER");
//        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
//        Date dateobj = new Date();
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(signUpLearnerRequest.getPhoneNo());
        signUpRequest.setRole(roles);
        signUpRequest.setPassword(signUpLearnerRequest.getPassword());

        Optional<String> userId = uaaClientService.signUp(signUpRequest);
        if (!userId.isPresent()) {
            throw new RuntimeException("Registration Failed");
        }

        Learners learners = new Learners();
        learners.setLearnerId(learnerId);
        learners.setAuthId(userId.get());
        learners.setIsActive(false);
        learners.setCurrentAddress(signUpLearnerRequest.getCurrentAddress());
        learners.setEmail(signUpLearnerRequest.getEmail());
        learners.setName(signUpLearnerRequest.getName());
        learners.setPhoneNo(signUpLearnerRequest.getPhoneNo());
        learners.setPresentWorkField(signUpLearnerRequest.getPresentWorkField());
        learnersRepository.save(learners);

        return new ResponseEntity(new IdentityResponse(learnerId), HttpStatus.CREATED);
    }

    public ResponseEntity<IdentityResponse> signUpForInstructor(SignUpInstructorRequest signUpInstructorRequest) {
        String instructorId = authUtil.getRandomIntNumber();
        Set<String> roles = new HashSet<>();
        roles.add("INSTRUCTOR");
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(signUpInstructorRequest.getPhoneNo());
        signUpRequest.setRole(roles);
        signUpRequest.setPassword(signUpInstructorRequest.getPassword());

        Optional<String> userId = uaaClientService.signUp(signUpRequest);
        if (!userId.isPresent()) {
            throw new RuntimeException("Registration Failed");
        }


        Instructors instructors = new Instructors();
        instructors.setInstructorId(instructorId);
        instructors.setAuthUuid(userId.get());
        instructors.setCurrentAddress(signUpInstructorRequest.getCurrentAddress());
        instructors.setEmail(signUpInstructorRequest.getEmail());
        instructors.setIsActive(false);
        instructors.setName(signUpInstructorRequest.getName());
        instructors.setPhoneNo(signUpInstructorRequest.getPhoneNo());
        instructors.setQualificationInfo(signUpInstructorRequest.getQualificationInfo());
        instructorsRepository.save(instructors);

        return new ResponseEntity(new IdentityResponse(instructorId), HttpStatus.CREATED);
    }

    public ResponseEntity<IdentityResponse> signUpForAdmin(SignUpAdminRequest signUpAdminRequest) {
        String adminId = authUtil.getRandomIntNumber();
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(signUpAdminRequest.getPhoneNo());
        signUpRequest.setRole(roles);
        signUpRequest.setPassword(signUpAdminRequest.getPassword());

        Optional<String> userId = uaaClientService.signUp(signUpRequest);
        if (!userId.isPresent()) {
            throw new RuntimeException("Registration Failed");
        }


        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setAuthUuid(userId.get());
        admin.setEmail(signUpAdminRequest.getEmail());
        admin.setName(signUpAdminRequest.getName());
        admin.setPhoneNo(signUpAdminRequest.getPhoneNo());
        adminRepository.save(admin);

        return new ResponseEntity(new IdentityResponse(adminId), HttpStatus.CREATED);
    }
    public boolean activeLoggedUser(String token) {

        String header = token;

        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(header);

        if (!loggedUserDetailsResponseOptional.isPresent()) {
            authUtil.setLogged(false);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Header Not Found");

        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();

        authUtil.setLoggedUserId(loggedUserDetailsResponse.getUserName());
        authUtil.setAuthenticate(loggedUserDetailsResponse.getIsAuthenticated());
        authUtil.setRoles(loggedUserDetailsResponse.getUserRole());
        authUtil.setLogged(true);
        return true;

    }

    public boolean pink(HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader("Authorization");
        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(header);

        if (!loggedUserDetailsResponseOptional.isPresent()) {

            return false;
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();
        System.out.println("----------------------------------------------" + loggedUserDetailsResponse.getUserRole());
        System.out.println("----------------------------------------------" + loggedUserDetailsResponse.getUserName());
        if (loggedUserDetailsResponse.getUserRole().get(0).equals("ADMIN")) {
            Admin admin = adminRepository.findAdminIdByPhoneNoNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(admin.getAdminId());
            authUtil.setLoggedUserName(admin.getName());
            authUtil.setLoggedUserPhoneNumber(admin.getPhoneNo());
            authUtil.setLoggedUserEmail(admin.getEmail());
            authUtil.setLoggedUserAddress("admin");
            System.out.println("Logged user id: " + admin.getAdminId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("INSTRUCTOR")) {
            Instructors instructor = instructorsRepository.findIdByPhoneNoNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(instructor.getInstructorId());
            authUtil.setLoggedUserName(instructor.getName());
            authUtil.setLoggedUserPhoneNumber(instructor.getPhoneNo());
            authUtil.setLoggedUserEmail(instructor.getEmail());
            authUtil.setLoggedUserAcountIsActive(instructor.getIsActive());
            authUtil.setLoggedUserQualification(instructor.getQualificationInfo());
            authUtil.setLoggedUserAddress(instructor.getCurrentAddress());
            System.out.println("Logged user id: " + instructor.getInstructorId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("LEARNER")) {
            Learners learner = learnersRepository.findIdByPhoneNoNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(learner.getLearnerId());
            authUtil.setLoggedUserName(learner.getName());
            authUtil.setLoggedUserPhoneNumber(learner.getPhoneNo());
            authUtil.setLoggedUserEmail(learner.getEmail());
            authUtil.setLoggedUserAcountIsActive(learner.getIsActive());
            authUtil.setLoggedUserAddress(learner.getCurrentAddress());
            System.out.println("Logged user id: " + learner.getLearnerId());
        }

        authUtil.setAuthenticate(loggedUserDetailsResponse.getIsAuthenticated());
        authUtil.setRoles(loggedUserDetailsResponse.getUserRole());
        authUtil.setLogged(true);
        return true;
    }

    public String getTest() {
        return authUtil.getLoggedUserId();
    }

    public ResponseEntity<String> logout() {
        if (!authUtil.isLogged()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not logged");
        }
        authUtil.setLogged(false);
        return new ResponseEntity("Logout Successfully", HttpStatus.OK);
    }


}
