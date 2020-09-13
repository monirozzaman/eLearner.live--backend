package live.elearners.controller;

import live.elearners.services.AuthService;
import live.elearners.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("user")
@CrossOrigin("*")
public class EmailVarifyController {
    private final AuthService authService;
    private final MailService mailService;

    @GetMapping("/verify")
    public ResponseEntity<Void> addNewReview(HttpServletRequest request) {
        authService.pink(request);
        return mailService.verify();
    }


}
