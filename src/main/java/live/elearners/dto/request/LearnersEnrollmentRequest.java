package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearnersEnrollmentRequest {
    private String paymentMethod;
    private String paymentTrxId;
    private String paid;

}
