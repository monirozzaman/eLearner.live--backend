package live.elearners.controller;

import live.elearners.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("user")
@CrossOrigin("*")
public class EmailVerifyController {

    private final MailService mailService;

    /*
     * GET Mapping
     * */
    @GetMapping("/verify/{userType}")
    public ResponseEntity<Void> addNewReview(@RequestParam("userId") String userId, @PathVariable String userType) {
        return mailService.verify(userId, userType);
    }


}
