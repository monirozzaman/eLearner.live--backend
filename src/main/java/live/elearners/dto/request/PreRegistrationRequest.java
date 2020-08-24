package live.elearners.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PreRegistrationRequest {
    String name;
    String phoneNo;
    String interestLevel;
    String email;
    String address;

}
