package live.elearners.services;


import live.elearners.client.UaaClientService;
import live.elearners.client.dto.request.SignUpRequest;
import live.elearners.client.dto.response.LoggedUserDetailsResponse;
import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.Learners;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.SignUpInstructorRequest;
import live.elearners.dto.request.SignUpLearnerRequest;
import live.elearners.dto.response.IdentityResponse;
import live.elearners.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final AuthUtil authUtil;


//    public ResponseEntity<IdentityResponse> login(UserLoginRequest userLoginRequest, HttpServletResponse response) {
//        String roleName = "";
//        Optional<AccessTokenResponse> accessTokenResponseOptional = uaaClientService.login(
//                new LoginRequest(userLoginRequest.getEmployeeId(), userLoginRequest.getPassword()));
//        if (!accessTokenResponseOptional.isPresent()) {
//
//        }
//
//        activeLoggedUser("Bearer " + accessTokenResponseOptional.get().getToken());
//        IdentityResponse identityResponse = new IdentityResponse(accessTokenResponseOptional.get().getToken());
//        return new ResponseEntity(identityResponse, HttpStatus.OK);
//    }

    public ResponseEntity<IdentityResponse> signUpForLearner(SignUpLearnerRequest signUpLearnerRequest) {
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
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
        learners.setLearnerId(userId.get());
        learners.setCurrentAddress(signUpLearnerRequest.getCurrentAddress());
        learners.setEmail(signUpLearnerRequest.getEmail());
        learners.setName(signUpLearnerRequest.getName());
        learners.setPhoneNo(signUpLearnerRequest.getPhoneNo());
        learners.setPresentWorkField(signUpLearnerRequest.getPresentWorkField());
        learnersRepository.save(learners);

        return new ResponseEntity(new IdentityResponse(userId.get()), HttpStatus.CREATED);
    }

    public ResponseEntity<IdentityResponse> signUpForInstructor(SignUpInstructorRequest signUpInstructorRequest) {
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
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
        instructors.setInstructorId(userId.get());
        instructors.setCurrentAddress(signUpInstructorRequest.getCurrentAddress());
        instructors.setEmail(signUpInstructorRequest.getEmail());
        instructors.setName(signUpInstructorRequest.getName());
        instructors.setPhoneNo(signUpInstructorRequest.getPhoneNo());
        instructors.setQualificationInfo(signUpInstructorRequest.getQualificationInfo());
        instructorsRepository.save(instructors);

        return new ResponseEntity(new IdentityResponse(userId.get()), HttpStatus.CREATED);
    }

    public boolean activeLoggedUser(String token) {

        String header = token;
        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(header);

        if (!loggedUserDetailsResponseOptional.isPresent()) {
            authUtil.setLogged(false);
            throw new ForbiddenException("Header Not Found");
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();

        authUtil.setEmployeeId(loggedUserDetailsResponse.getUserName());
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

        authUtil.setEmployeeId(loggedUserDetailsResponse.getUserName());
        authUtil.setAuthenticate(loggedUserDetailsResponse.getIsAuthenticated());
        authUtil.setRoles(loggedUserDetailsResponse.getUserRole());
        authUtil.setLogged(true);
        return true;
    }

    public String getTest() {
        return authUtil.getEmployeeId();
    }

    public ResponseEntity<String> logout() {
        if (!authUtil.isLogged()) {
            throw new ForbiddenException("User is not logged");
        }
        authUtil.setLogged(false);
        return new ResponseEntity("Logout Successfully", HttpStatus.OK);
    }
}
