package live.elearners.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Table;


@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Table(name = "qualificationInfo")
public class QualificationInfo {

    String qualification;
    String designation;
    String companyName;
    String totalProfessionalExperienceInYear;
}
