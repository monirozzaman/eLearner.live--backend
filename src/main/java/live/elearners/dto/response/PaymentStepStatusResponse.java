package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentStepStatusResponse {
    String step;
    Boolean paymentStatus;
}
