package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpLearnerRequest {
    private String name;
    private String email;
    private String currentAddress;
    private String presentWorkField;
    private String phoneNo;
    private String password;

}
