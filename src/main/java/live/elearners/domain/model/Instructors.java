package live.elearners.domain.model;

import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Audited
@Table(name = "instructors")
public class Instructors extends Auditable<String> {
    @Id
    String instructorId;

    String authUuid;

    String name;

    String email;

    String currentAddress;

    @Embedded
    QualificationInfo qualificationInfo;

    String phoneNo;

    Boolean isActive;

    Boolean isEmailVerified;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image")
    private ImageDetails imageDetails;

}
