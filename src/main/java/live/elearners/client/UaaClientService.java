package live.elearners.client;


import live.elearners.client.dto.request.LoginClientRequest;
import live.elearners.client.dto.request.SignUpRequest;
import live.elearners.client.dto.response.AccessTokenResponse;
import live.elearners.client.dto.response.LoggedUserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
@FeignClient(value = "UaaClientService", url = "http://167.99.76.96:20230/")
public interface UaaClientService {

    @PostMapping("public/signin")
    Optional<AccessTokenResponse> login(LoginClientRequest loginRequest);

    @PostMapping("public/signup")
    Optional<String> signUp(SignUpRequest signUpRequest);

    @GetMapping("user-details")
    Optional<LoggedUserDetailsResponse> getLoggedUserDetails(@RequestHeader("Authorization") String authorizationHeader);


}
