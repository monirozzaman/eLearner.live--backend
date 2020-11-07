package live.elearners.domain.model;


import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Audited
@Table(name = "learners")
public class Learners extends Auditable<String> {

    @Id
    String learnerId;

    String authId;

    String name;

    String email;

    String currentAddress;

    String presentWorkField;

    String phoneNo;

    Boolean isActive;

    Boolean isEmailVerified;

    int paymentStep;

    @OneToMany(targetEntity = RegisteredCourses.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "f_key", referencedColumnName = "learnerId")
    private List<RegisteredCourses> registeredCourses;

}
