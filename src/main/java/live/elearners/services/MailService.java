package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Admin;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.Learners;
import live.elearners.domain.repository.AdminRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.EmailSentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;


@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private LearnersRepository learnersRepository;
    private InstructorsRepository instructorsRepository;
    private AdminRepository adminRepository;
    private AuthUtil authUtil;

    public MailService(LearnersRepository learnersRepository, InstructorsRepository instructorsRepository, AdminRepository adminRepository, AuthUtil authUtil) {
        this.learnersRepository = learnersRepository;
        this.instructorsRepository = instructorsRepository;
        this.adminRepository = adminRepository;
        this.authUtil = authUtil;
    }

    @Value("${email.from.address}")
    private String fromAddress;


    public void sendMailMultipart(EmailSentRequest emailSentRequest, MultipartFile file) throws MessagingException, IOException {

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Hello there</h1>\n" +
                "<p>" + emailSentRequest.getBody() + "</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        for (String toEmail : emailSentRequest.getTo()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject(emailSentRequest.getSubject());
            helper.setText(html, true);

            if (file != null) {
                FileSystemResource files = new FileSystemResource(convertMultiPartToFile(file));
                helper.addAttachment(file.getOriginalFilename(), files);
            }
            javaMailSender.send(mimeMessage);
        }
    }

    public void sendVerificationMail(String to, String subject, String body) {

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Hello there</h1>\n" +
                "<p>" + body + "</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public ResponseEntity<Void> verify(String userId, String userType) {

        switch (userType.toUpperCase()) {
            case "ADMIN":

                Optional<Admin> optionalAdmin = adminRepository.findById(userId);
                if (!optionalAdmin.isPresent()) {
                } else {
                    Admin admin = optionalAdmin.get();
                    admin.setIsEmailVerified(true);
                    adminRepository.save(admin);
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            case "INSTRUCTOR":
                Optional<Instructors> optionalInstructors = instructorsRepository.findById(userId);
                if (!optionalInstructors.isPresent()) {
                } else {
                    Instructors instructors = optionalInstructors.get();
                    instructors.setIsEmailVerified(true);
                    instructorsRepository.save(instructors);
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            case "LEARNER":
                Optional<Learners> optionalLearners = learnersRepository.findById(userId);
                if (!optionalLearners.isPresent()) {
                } else {
                    Learners learners = optionalLearners.get();
                    learners.setIsEmailVerified(true);
                    learnersRepository.save(learners);
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            default:
                return new ResponseEntity(null, HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity(null, HttpStatus.FORBIDDEN);
    }
}
