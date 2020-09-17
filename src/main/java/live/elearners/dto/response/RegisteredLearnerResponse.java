package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredLearnerResponse {

    private String learnerId;

    private String learnerName;

    private String learnerPhoneNO;

    private String learnerEmail;

    private String paymentDateAndTime;

    private String paymentVerifyDateAndTime;

    private boolean isPaymentVerified;

    private String paymentMethod;

    private String paymentTrxId;

    private String paid;

    private String due;

    private String commitmentDuePaidDate;

}
