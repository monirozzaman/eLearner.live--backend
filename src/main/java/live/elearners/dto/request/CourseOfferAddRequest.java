package live.elearners.dto.request;

import live.elearners.domain.model.Offers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseOfferAddRequest {

    private String offerInPerchance;
    private String offerReason;
    private String offerStatDate;
    private String offerEndDate;


}
