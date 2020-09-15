package live.elearners.controller;

import live.elearners.services.AuthService;
import live.elearners.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("user")
@CrossOrigin("*")
public class EmailVarifyController {
    private final AuthService authService;
    private final MailService mailService;

    @GetMapping("/verify/{userType}")
    public ResponseEntity<Void> addNewReview(@RequestParam("userId") String userId, @PathVariable String userType) {
        return mailService.verify(userId, userType);
    }


}
