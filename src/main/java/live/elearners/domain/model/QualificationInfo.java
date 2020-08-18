package live.elearners.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;


@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class QualificationInfo {

    String qualification;
    String designation;
    String companyName;
    String totalProfessionalExperienceInYear;
}
