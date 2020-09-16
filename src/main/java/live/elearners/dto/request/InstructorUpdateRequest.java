package live.elearners.dto.request;

import live.elearners.domain.model.QualificationInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InstructorUpdateRequest {
    String name;
    String currentAddress;
    QualificationInfo qualificationInfo;
    String phoneNo;

}
