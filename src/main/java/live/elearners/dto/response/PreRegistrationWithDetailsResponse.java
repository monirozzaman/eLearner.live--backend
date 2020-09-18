package live.elearners.dto.response;

import live.elearners.domain.model.ImageDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreRegistrationWithDetailsResponse {
    String preRegistrationId;
    String courseId;
    String courseName;
    String orientationDateTime;
    private ImageDetails imageDetails;
}
