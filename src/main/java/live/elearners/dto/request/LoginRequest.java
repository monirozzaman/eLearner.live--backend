package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    String phoneNo;

    String password;
}
