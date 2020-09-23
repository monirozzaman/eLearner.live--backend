package live.elearners.controller;

import com.google.gson.Gson;
import live.elearners.client.dto.response.AccessTokenResponse;
import live.elearners.dto.request.*;
import live.elearners.dto.response.IdentityResponse;
import live.elearners.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class SignInAndSignUpController {

    private final AuthService authService;


    @PostMapping("sign-up/learner")
    public ResponseEntity<IdentityResponse> signUpLearner(@RequestBody SignUpLearnerRequest signUpLearnerRequest) {

        return authService.signUpForLearner(signUpLearnerRequest);
    }

    @PostMapping("sign-up/instructor")
    public ResponseEntity<IdentityResponse> signUpInstructor(HttpServletRequest httpServletRequest, @RequestParam("signUpInstructorRequestInString") String signUpInstructorRequestInString,
                                                             @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        SignUpInstructorRequest signUpInstructorRequest = g.fromJson(signUpInstructorRequestInString, SignUpInstructorRequest.class);
        return authService.signUpForInstructor(signUpInstructorRequest, file);
    }

    @PostMapping("sign-up/admin")
    public ResponseEntity<IdentityResponse> signUpAdmin(HttpServletRequest httpServletRequest, @RequestBody SignUpAdminRequest signUpAdminRequest) {
        authService.pink(httpServletRequest);
        return authService.signUpForAdmin(signUpAdminRequest);
    }

    @PostMapping("login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PutMapping("reset")
    public ResponseEntity<String> reset(@RequestBody ResetPasswordForm resetPasswordForm, @RequestParam("resetId") String resetId) {

        return authService.reset(resetPasswordForm, resetId);
    }

    @GetMapping("reset/{email}")
    public ResponseEntity<String> sendEmailForResetPassword(@PathVariable("email") String email) {

        return authService.sendEmailForResetPassword(email);
    }
    @GetMapping("getLoggedUserDetails")
    public ResponseEntity<Object> getLoggedUserDetails(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return authService.getLoggedUserDetails();
    }

}
