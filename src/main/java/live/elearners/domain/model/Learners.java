package live.elearners.domain.model;


import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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


}
