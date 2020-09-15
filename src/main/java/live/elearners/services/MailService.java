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

                System.out.println("sending to " + email);
            }


        } catch (MessagingException e) {
            throw new RuntimeException("Sending failed " + e);
        }


//
//        String html = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "<title>Page Title</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<h1>Hello there</h1>\n" +
//                "<p>" + emailSentRequest.getBody() + "</p>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>";
//
//        for (String toEmail : emailSentRequest.getTo()) {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setFrom(fromAddress);
//            helper.setTo(toEmail);
//            helper.setSubject(emailSentRequest.getSubject());
//            helper.setText(html, true);
//
//            if (file != null) {
//                FileSystemResource files = new FileSystemResource(convertMultiPartToFile(file));
//                helper.addAttachment(file.getOriginalFilename(), files);
//            }
//            javaMailSender.send(mimeMessage);
//        }
    }

    //    public void sendVerificationMail(String to, String subject, String body) {
//
//        String html = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "<title>Page Title</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<h1>Hello there</h1>\n" +
//                "<p>" + body + "</p>\n" +
//                "\n" +
//                "</body>\n" +
//                "</html>";
//
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = null;
//            helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setFrom(fromAddress);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(html, true);
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//
//    }
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
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(html, "utf-8", "html");
            ;

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

//            messageBodyPart = new MimeBodyPart();
//            DataSource source =
//                    new FileDataSource(outputFile);
//            messageBodyPart.setDataHandler(
//                    new DataHandler(source));
//            messageBodyPart.setFileName("MyFile.pdf");
//            multipart.addBodyPart(messageBodyPart);
//
//            // Put parts in message
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
