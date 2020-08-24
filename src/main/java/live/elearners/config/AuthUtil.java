package live.elearners.config;

import live.elearners.domain.model.QualificationInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
}
