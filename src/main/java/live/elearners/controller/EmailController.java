package live.elearners.controller;

import com.google.gson.Gson;
import live.elearners.dto.request.EmailSentRequest;
import live.elearners.services.AuthService;
import live.elearners.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
@RequestMapping("email")
@CrossOrigin("*")
public class EmailController {
    private final AuthService authService;
    private final MailService emailService;

    @PostMapping("/sent")
    public void send(HttpServletRequest httpServletRequest,
                     @RequestParam("emailSentRequestInString") String emailSentRequestInString,
                     @RequestParam("file") MultipartFile file) throws MessagingException {
        Gson g = new Gson();
        EmailSentRequest emailSentRequest = g.fromJson(emailSentRequestInString, EmailSentRequest.class);
        emailService.sendMailMultipart(emailSentRequest, file);
    }
}
