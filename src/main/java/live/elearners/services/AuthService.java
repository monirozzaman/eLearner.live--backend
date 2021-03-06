package live.elearners.services;


import com.itvillage.AES;
import live.elearners.client.UaaClientService;
import live.elearners.client.dto.request.LoginClientRequest;
import live.elearners.client.dto.request.SignUpRequest;
import live.elearners.client.dto.response.AccessTokenResponse;
import live.elearners.client.dto.response.LoggedUserDetailsResponse;
import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.Admin;
import live.elearners.domain.model.ImageDetails;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.Learners;
import live.elearners.domain.repository.AdminRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.*;
import live.elearners.dto.response.IdentityResponse;
import live.elearners.exception.ForbiddenException;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private final MailService mailService;
    private final AuthUtil authUtil;
    private final FileStorageService fileStorageService;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public ResponseEntity<AccessTokenResponse> login(LoginRequest loginRequest) {
        Learners existinglearners = learnersRepository.findIdByEmailNative(loginRequest.getEmail());
        Instructors existingInstructors = instructorsRepository.findIdByEmailNative(loginRequest.getEmail());
        Admin existingAdmin = adminRepository.findAdminIdByEmailNative(loginRequest.getEmail());

        if (existinglearners == null && existingInstructors == null && existingAdmin == null) {
            return new ResponseEntity(new AccessTokenResponse("Email not registered"), HttpStatus.NOT_FOUND);
        }

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        logger.debug("Logged User Email: " + email);
        if (email != null && password != null) {
            Optional<AccessTokenResponse> accessTokenResponseOptional = uaaClientService.login(
                    new LoginClientRequest(email, password));
            if (!accessTokenResponseOptional.isPresent()) {
                return new ResponseEntity(new AccessTokenResponse("User Not Registered"), HttpStatus.FORBIDDEN);
            }
            boolean isVerifiedAndSetUser = checkEmailIsVerified("Bearer " + accessTokenResponseOptional.get().getToken());

            if (isVerifiedAndSetUser) {
                AccessTokenResponse tokenResponse = new AccessTokenResponse(accessTokenResponseOptional.get().getToken());
                pinkByAccessToken("Bearer " + accessTokenResponseOptional.get().getToken());

                return new ResponseEntity(tokenResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(new AccessTokenResponse("Email is not verified"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(new AccessTokenResponse("Email or password is empty"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<IdentityResponse> signUpForLearner(SignUpLearnerRequest signUpLearnerRequest) {

        Learners existinglearners = learnersRepository.findIdByEmailNative(signUpLearnerRequest.getEmail());
        Instructors existingInstructors = instructorsRepository.findIdByEmailNative(signUpLearnerRequest.getEmail());
        Admin existingAdmin = adminRepository.findAdminIdByEmailNative(signUpLearnerRequest.getEmail());

        if (existinglearners == null && existingInstructors == null && existingAdmin == null) {

            Set<String> roles = new HashSet<>();
            String getCurrentDate = authUtil.getCurrentDate().replaceAll("/", "");
            String learnerId = getCurrentDate + authUtil.getRandomIntNumber();
            roles.add("LEARNER");
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setUserId(learnerId);
            signUpRequest.setUsername(signUpLearnerRequest.getEmail());
            signUpRequest.setRole(roles);
            signUpRequest.setPassword(signUpLearnerRequest.getPassword());

            Optional<String> userId = uaaClientService.signUp(signUpRequest);

            if (!userId.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found From Security Panel");
            }

            Learners learners = new Learners();
            learners.setLearnerId(learnerId);
            learners.setAuthId(userId.get());
            learners.setIsActive(true);
            learners.setCurrentAddress(signUpLearnerRequest.getCurrentAddress());
            learners.setEmail(signUpLearnerRequest.getEmail());
            learners.setName(signUpLearnerRequest.getName());
            learners.setPhoneNo(signUpLearnerRequest.getPhoneNo());
            learners.setPresentWorkField(signUpLearnerRequest.getPresentWorkField());
            learners.setIsEmailVerified(false);
            learners.setPaymentStep(0);
            //For Verification Mail
            mailService.sendVerificationMail(signUpLearnerRequest.getEmail(), "Email Verification Required", "http://dev.elearners.live/user/verify/learner?userId=" + learnerId);
            learnersRepository.save(learners);

            return new ResponseEntity(new IdentityResponse(learnerId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new IdentityResponse("Email Already Registered"), HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<IdentityResponse> signUpForInstructor(SignUpInstructorRequest signUpInstructorRequest, MultipartFile file) {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            Learners existinglearners = learnersRepository.findIdByEmailNative(signUpInstructorRequest.getEmail());
            Instructors existingInstructors = instructorsRepository.findIdByEmailNative(signUpInstructorRequest.getEmail());
            Admin existingAdmin = adminRepository.findAdminIdByEmailNative(signUpInstructorRequest.getEmail());

            if (existinglearners == null && existingInstructors == null && existingAdmin == null) {
                String instructorId = authUtil.getRandomIntNumber();
                Set<String> roles = new HashSet<>();
                roles.add("INSTRUCTOR");
                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setUserId(instructorId);
                signUpRequest.setUsername(signUpInstructorRequest.getEmail());
                signUpRequest.setRole(roles);
                signUpRequest.setPassword(signUpInstructorRequest.getPassword());

                Optional<String> userId = uaaClientService.signUp(signUpRequest);
                if (!userId.isPresent()) {
                    throw new RuntimeException("Registration Failed");
                }
                /*Start upload image*/
                String fileName = null;
                String fileDownloadUri = null;

                if (!file.isEmpty()) {
                    fileName = fileStorageService.storeFile(file, file.getOriginalFilename());
                    fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/view/")
                            .path(fileName)
                            .toUriString();
                } else {
                    System.err.println("File Not found");
                }

                ImageDetails imageDetails = new ImageDetails();
                imageDetails.setName(fileName);
                imageDetails.setType(file.getContentType());
                imageDetails.setImageUrl(fileDownloadUri);
                /*End upload image*/
                Instructors instructors = new Instructors();
                instructors.setInstructorId(instructorId);
                instructors.setAuthUuid(userId.get());
                instructors.setCurrentAddress(signUpInstructorRequest.getCurrentAddress());
                instructors.setEmail(signUpInstructorRequest.getEmail());
                instructors.setIsActive(true);
                instructors.setName(signUpInstructorRequest.getName());
                instructors.setPhoneNo(signUpInstructorRequest.getPhoneNo());
                instructors.setQualificationInfo(signUpInstructorRequest.getQualificationInfo());
                instructors.setImageDetails(imageDetails);
                instructors.setIsEmailVerified(true);
                instructorsRepository.save(instructors);

                return new ResponseEntity(new IdentityResponse(instructorId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity(new IdentityResponse("Email Already Registered"), HttpStatus.BAD_REQUEST);

            }
        } else {
            return new ResponseEntity(new IdentityResponse("Access Deny! You are not admin"), HttpStatus.FORBIDDEN);
        }

    }

    public ResponseEntity<IdentityResponse> signUpForAdmin(SignUpAdminRequest signUpAdminRequest) {
        if (authUtil.getRole().equals("ROLE_ADMIN")) {
            Learners existinglearners = learnersRepository.findIdByEmailNative(signUpAdminRequest.getEmail());
            Instructors existingInstructors = instructorsRepository.findIdByEmailNative(signUpAdminRequest.getEmail());
            Admin existingAdmin = adminRepository.findAdminIdByEmailNative(signUpAdminRequest.getEmail());

            if (existinglearners == null && existingInstructors == null && existingAdmin == null) {

                String adminId = authUtil.getRandomIntNumber();
                Set<String> roles = new HashSet<>();
                roles.add("ADMIN");

                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setUserId(adminId);
                signUpRequest.setUsername(signUpAdminRequest.getEmail());
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
                admin.setIsEmailVerified(false);
                adminRepository.save(admin);
                mailService.sendVerificationMail(signUpAdminRequest.getEmail(), "Email Verification Require", "http://dev.elearners.live/user/verify/admin?userId=" + adminId);
                return new ResponseEntity(new IdentityResponse(adminId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity(new IdentityResponse("Email Already Registered"), HttpStatus.BAD_REQUEST);

            }
        } else {
            return new ResponseEntity(new IdentityResponse("Access Deny"), HttpStatus.FORBIDDEN);
        }
    }

    public boolean checkEmailIsVerified(String token) {

        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(token);

        if (!loggedUserDetailsResponseOptional.isPresent()) {

            throw new ForbiddenException("User Details Not Found");
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();
        if (loggedUserDetailsResponse.getUserRole().get(0).equals("ROLE_ADMIN")) {
            Admin admin = adminRepository.findAdminIdByEmailNative(loggedUserDetailsResponse.getUserName());
            return admin.getIsEmailVerified();

        }
        if (loggedUserDetailsResponse.getUserRole().get(0).equals("ADMIN")) {
            Admin admin = adminRepository.findAdminIdByEmailNative(loggedUserDetailsResponse.getUserName());
            return admin.getIsEmailVerified();

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("INSTRUCTOR")) {
            Instructors instructor = instructorsRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
            return instructor.getIsEmailVerified();

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("LEARNER")) {
            Learners learner = learnersRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
            return learner.getIsEmailVerified();
        } else {
            return false;
        }
    }

    public boolean pink(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if (header == null) {
            throw new ForbiddenException("Header Not Found");
        }

        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(header);

        if (!loggedUserDetailsResponseOptional.isPresent()) {
            return false;
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();

        logger.debug("Logged User Role" + loggedUserDetailsResponse.getUserRole());
        logger.debug("Logged User Name" + loggedUserDetailsResponse.getUserName());

        if (loggedUserDetailsResponse.getUserRole().get(0).equals("ADMIN")) {
            Admin admin = adminRepository.findAdminIdByEmailNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(admin.getAdminId());
            authUtil.setLoggedUserName(admin.getName());
            authUtil.setLoggedUserPhoneNumber(admin.getPhoneNo());
            authUtil.setLoggedUserEmail(admin.getEmail());
            authUtil.setLoggedUserAddress("admin");
            logger.debug("Logged user id: " + admin.getAdminId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("INSTRUCTOR")) {
            Instructors instructor = instructorsRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(instructor.getInstructorId());
            authUtil.setLoggedUserName(instructor.getName());
            authUtil.setLoggedUserPhoneNumber(instructor.getPhoneNo());
            authUtil.setLoggedUserEmail(instructor.getEmail());
            authUtil.setLoggedUserAcountIsActive(instructor.getIsActive());
            authUtil.setLoggedUserQualification(instructor.getQualificationInfo());
            authUtil.setLoggedUserAddress(instructor.getCurrentAddress());
            logger.debug("Logged user id: " + instructor.getInstructorId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("LEARNER")) {
            Learners learner = learnersRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(learner.getLearnerId());
            authUtil.setLoggedUserName(learner.getName());
            authUtil.setLoggedUserPhoneNumber(learner.getPhoneNo());
            authUtil.setLoggedUserEmail(learner.getEmail());
            authUtil.setLoggedUserAcountIsActive(learner.getIsActive());
            authUtil.setLoggedUserAddress(learner.getCurrentAddress());
            logger.debug("Logged user id: " + learner.getLearnerId());
        }

        authUtil.setAuthenticate(loggedUserDetailsResponse.getIsAuthenticated());
        authUtil.setRoles(loggedUserDetailsResponse.getUserRole());
        authUtil.setLogged(true);
        return true;
    }

    public boolean pinkByAccessToken(String token) {

        Optional<LoggedUserDetailsResponse> loggedUserDetailsResponseOptional = uaaClientService.getLoggedUserDetails(token);

        if (!loggedUserDetailsResponseOptional.isPresent()) {

            return false;
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = loggedUserDetailsResponseOptional.get();
        System.out.println("----------------------------------------------" + loggedUserDetailsResponse.getUserRole());
        System.out.println("----------------------------------------------" + loggedUserDetailsResponse.getUserName());
        if (loggedUserDetailsResponse.getUserRole().get(0).equals("ADMIN")) {
            Admin admin = adminRepository.findAdminIdByEmailNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(admin.getAdminId());
            authUtil.setLoggedUserName(admin.getName());
            authUtil.setLoggedUserPhoneNumber(admin.getPhoneNo());
            authUtil.setLoggedUserEmail(admin.getEmail());
            authUtil.setLoggedUserAddress("admin");
            System.out.println("Logged user id: " + admin.getAdminId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("INSTRUCTOR")) {
            Instructors instructor = instructorsRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
            authUtil.setLoggedUserId(instructor.getInstructorId());
            authUtil.setLoggedUserName(instructor.getName());
            authUtil.setLoggedUserPhoneNumber(instructor.getPhoneNo());
            authUtil.setLoggedUserEmail(instructor.getEmail());
            authUtil.setLoggedUserAcountIsActive(instructor.getIsActive());
            authUtil.setLoggedUserQualification(instructor.getQualificationInfo());
            authUtil.setLoggedUserAddress(instructor.getCurrentAddress());
            System.out.println("Logged user id: " + instructor.getInstructorId());

        } else if (loggedUserDetailsResponse.getUserRole().get(0).equals("LEARNER")) {
            Learners learner = learnersRepository.findIdByEmailNative(loggedUserDetailsResponse.getUserName());
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


    public ResponseEntity<Object> getLoggedUserDetails() {

        switch (authUtil.getRole()) {
            case "ADMIN":
                Optional<Admin> optionalAdmin = adminRepository.findById(authUtil.getLoggedUserId());
                if (optionalAdmin.isPresent()) {
                    return new ResponseEntity(optionalAdmin.get(), HttpStatus.OK);
                }
                break;
            case "INSTRUCTOR":
                Optional<Instructors> optionalInstructors = instructorsRepository.findById(authUtil.getLoggedUserId());
                if (optionalInstructors.isPresent()) {
                    return new ResponseEntity(optionalInstructors.get(), HttpStatus.OK);
                }
                break;
            case "LEARNER":
                Optional<Learners> optionalLearners = learnersRepository.findById(authUtil.getLoggedUserId());
                if (optionalLearners.isPresent()) {
                    return new ResponseEntity(optionalLearners.get(), HttpStatus.OK);
                }
                break;
            default:
                return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> reset(ResetPasswordForm resetPasswordForm, String resetId) {
        String userId = AES.decrypt(resetId, "elearners.live428854@#");
        Optional<String> responseOptional = uaaClientService.reset(resetPasswordForm, userId);
        if (!responseOptional.isPresent()) {
            throw new ResourseNotFoundException("Unsuccessful");
        } else {
            return new ResponseEntity(responseOptional.get(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> sendEmailForResetPassword(String email) {
        Learners existinglearners = learnersRepository.findIdByEmailNative(email);
        Instructors existingInstructors = instructorsRepository.findIdByEmailNative(email);
        Admin existingAdmin = adminRepository.findAdminIdByEmailNative(email);
        if (existinglearners != null) {
            mailService.sendEmailFOrVerificationForPasswordReset(email, "Reset Password",
                    "http://dev.elearners.live/reset?resetId=" + AES.encrypt(existinglearners.getLearnerId(),
                            "elearners.live428854@#"));
            return new ResponseEntity("Password Reset Successful", HttpStatus.OK);
        } else if (existingInstructors != null) {
            mailService.sendEmailFOrVerificationForPasswordReset(email, "Reset Password",
                    "http://dev.elearners.live/reset?resetId=" + AES.encrypt(existingInstructors.getInstructorId(),
                            "elearners.live428854@#"));
            return new ResponseEntity("Password Reset Successful", HttpStatus.OK);
        } else if (existingAdmin != null) {
            mailService.sendEmailFOrVerificationForPasswordReset(email, "Reset Password",
                    "http://dev.elearners.live/reset?resetId=" + AES.encrypt(existingAdmin.getAdminId(),
                            "elearners.live428854@#"));
            return new ResponseEntity("Password Reset Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity("Email not found", HttpStatus.NOT_FOUND);
        }
    }
}
