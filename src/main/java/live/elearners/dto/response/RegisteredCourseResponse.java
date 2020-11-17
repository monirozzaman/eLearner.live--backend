package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisteredCourseResponse {

    private String courseName;
    private String paymentDateAndTime;
    private String paymentVerifyDateAndTime;
    private boolean isPaymentVerified;
    private String paymentMethod;
    private String paymentTrxId;
    private String paid;
    private String due;
    private String commitmentDuePaidDate;
    private String enrollmentStepNo;


}
