package live.elearners.controller;

import live.elearners.client.dto.response.AccessTokenResponse;
import live.elearners.dto.request.LoginRequest;
import live.elearners.dto.request.SignUpInstructorRequest;
import live.elearners.dto.request.SignUpLearnerRequest;
import live.elearners.dto.response.IdentityResponse;
import live.elearners.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SignInAndSignUpController {

    private final AuthService authService;


    @PostMapping("sign-up/learner")
    public ResponseEntity<IdentityResponse> signUpLearner(@RequestBody SignUpLearnerRequest signUpLearnerRequest) {

        return authService.signUpForLearner(signUpLearnerRequest);
    }

    @PostMapping("sign-up/instructor")
    public ResponseEntity<IdentityResponse> signUpLearner(@RequestBody SignUpInstructorRequest signUpLearnerRequest) {

        return authService.signUpForInstructor(signUpLearnerRequest);
    }

    @GetMapping("login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }
}
