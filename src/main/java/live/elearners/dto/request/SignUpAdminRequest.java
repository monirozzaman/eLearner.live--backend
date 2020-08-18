package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpAdminRequest {
    String name;
    String email;
    String phoneNo;
    String password;
}
