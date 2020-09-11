package live.elearners.config;

import live.elearners.domain.model.QualificationInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
@Setter
@Getter
public class AuthUtil {

    private String loggedUserId;

    private List<String> roles;

    private boolean isAuthenticate;

    private boolean isLogged;

    private String loggedUserName;

    private String loggedUserPhoneNumber;

    private String loggedUserEmail;

    private String loggedUserAddress;

    private QualificationInfo loggedUserQualification;

    private boolean loggedUserAcountIsActive;


    public boolean isLogged() {
        return isLogged;
    }

    public String getRole() {
        String role = "";
        for (String roleValue : roles) {
            role = roleValue;
        }
        return role;
    }


    public boolean isAuthenticate() {
        return isAuthenticate;
    }

    public String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public String getCurrentDateAndTime() {
        DateFormat df = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
        Calendar calobj = Calendar.getInstance();
        return df.format(calobj.getTime());
    }

    public String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calobj = Calendar.getInstance();
        return df.format(calobj.getTime());
    }

    public String getCurrentTime() {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        Calendar calobj = Calendar.getInstance();
        return df.format(calobj.getTime());
    }

    public String getRandomIntNumber() {
        int rand = (new Random().nextInt(900000) + 100000);
        return String.valueOf(rand);

    }

    // compress the image bytes before storing it in the database
    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public static String getRandomIntNumberForImages() {
        int rand = (new Random().nextInt(9000) + 1000);
        return String.valueOf(rand);

    }
}
