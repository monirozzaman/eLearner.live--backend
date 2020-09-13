package live.elearners.services;

import live.elearners.dto.request.EmailSentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from.address}")
    private String fromAddress;

    public void sendEmail(EmailSentRequest emailSentRequest) throws MailException, MessagingException {

//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(emailSentRequest.getTo().get(0));
//        mail.setSubject(emailSentRequest.getSubject());
//        mail.setText(emailSentRequest.getBody());
//
//        javaMailSender.send(mail);
//        sendMailMultipart("eproni29@gmail.com", 5, null, "6");
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

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
