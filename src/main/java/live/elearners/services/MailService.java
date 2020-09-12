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

    public void sendMailMultipart(EmailSentRequest emailSentRequest, MultipartFile file) throws MessagingException {

        System.err.println(file.getOriginalFilename());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromAddress);
        helper.setTo(emailSentRequest.getTo().get(0));
        helper.setSubject(emailSentRequest.getSubject());
        helper.setText(emailSentRequest.getBodyInHtml(), true);

        if (file != null) {
            FileSystemResource allFiles = new FileSystemResource(new File(file.getOriginalFilename()));
            helper.addAttachment(file.getName(), allFiles);
        }
        javaMailSender.send(mimeMessage);
    }


}
