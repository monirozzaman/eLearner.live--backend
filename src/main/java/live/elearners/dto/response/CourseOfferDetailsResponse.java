package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseOfferDetailsResponse {
    private String offerReason;
    private double offerPrice;
    private String offerFinishDateAndTime;

}
