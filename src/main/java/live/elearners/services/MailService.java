package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Admin;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.model.Learners;
import live.elearners.domain.repository.AdminRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.EmailSentRequest;
import live.elearners.exception.ResourseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;


@Service
public class MailService {

    private final static String USERNAME = "itvillage29@gmail.com";
    private final static String PASSWORD = "itvillage428854@#";
    @Autowired
    private JavaMailSender javaMailSender;
    private LearnersRepository learnersRepository;
    private InstructorsRepository instructorsRepository;
    private AdminRepository adminRepository;
    private AuthUtil authUtil;
    @Value("${email.from.address}")
    private String fromAddress;
    private Logger logger = LoggerFactory.getLogger(MailService.class);

    public MailService(LearnersRepository learnersRepository, InstructorsRepository instructorsRepository, AdminRepository adminRepository, AuthUtil authUtil) {
        this.learnersRepository = learnersRepository;
        this.instructorsRepository = instructorsRepository;
        this.adminRepository = adminRepository;
        this.authUtil = authUtil;
    }

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
        final String username = "itvillage29@gmail.com";
        final String password = "itvillage428854@#";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            for (String email : emailSentRequest.getTo()) {
                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                message.setSubject(emailSentRequest.getSubject());

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(html, "utf-8", "html");


                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(convertMultiPartToFile(file));
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getOriginalFilename());
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

                Transport.send(message);

                logger.info("Sending to " + email);
            }


        } catch (MessagingException e) {
            throw new RuntimeException("Sending failed " + e);
        }


    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File("mail-images/" + file.getOriginalFilename());
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
                    throw new ResourseNotFoundException("Admin Not Found");
                } else {
                    Admin admin = optionalAdmin.get();
                    admin.setIsEmailVerified(true);
                    adminRepository.save(admin);
                    return new ResponseEntity(HttpStatus.OK);
                }

            case "INSTRUCTOR":
                Optional<Instructors> optionalInstructors = instructorsRepository.findById(userId);
                if (!optionalInstructors.isPresent()) {
                    throw new ResourseNotFoundException("Instructor Not Found");
                } else {
                    Instructors instructors = optionalInstructors.get();
                    instructors.setIsEmailVerified(true);
                    instructorsRepository.save(instructors);
                    return new ResponseEntity(HttpStatus.OK);
                }
            case "LEARNER":
                Optional<Learners> optionalLearners = learnersRepository.findById(userId);
                if (!optionalLearners.isPresent()) {
                    throw new ResourseNotFoundException("Learner Not Found");
                } else {
                    Learners learners = optionalLearners.get();
                    learners.setIsEmailVerified(true);
                    learnersRepository.save(learners);
                    return new ResponseEntity(HttpStatus.OK);
                }
            default:
                return new ResponseEntity(null, HttpStatus.FORBIDDEN);

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


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        sendMail(to, subject, html, session);
    }

    public void sendEmailFOrVerificationForPasswordReset(String to, String subject, String body) {
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


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        sendMail(to, subject, html, session);
    }

    private void sendMail(String to, String subject, String html, Session session) {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(html, "utf-8", "html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            logger.info("Mail Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
