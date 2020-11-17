package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LearnerResponse {

    String learnerId;

    String authId;

    String name;

    String email;

    String currentAddress;

    String presentWorkField;

    String phoneNo;

    Boolean isActive;

    Boolean isEmailVerified;

    int paymentStep;

    List<RegisteredCourseResponse> registeredCourseResponses;
}
