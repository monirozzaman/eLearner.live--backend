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
@Table(name = "elearner_admin")
public class Admin extends Auditable<String> {
    @Id
    String adminId;
    String authUuid;
    String name;
    String email;
    String phoneNo;
    Boolean isEmailVerified;
}
