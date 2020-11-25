package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseOfferAddRequest {


    private boolean isBasicOfferEnable;

    private int basicOfferInPercentage;

    private String basicOfferDetails;

    private int specialOfferInLowestPercentage;

    private int specialOfferInHighestPercentage;

    private int specialOfferIncrementPercentage;

    private int specialOfferIncrementAfterTimeDuration;

    private String specialOfferDetails;

    private String specialOfferStatDate;

    private String specialOfferEndDate;


}
