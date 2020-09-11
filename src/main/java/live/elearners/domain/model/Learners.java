package live.elearners.domain.model;


import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Audited
public class Learners extends Auditable<String> {

    @Id
    String learnerId;

    @Column
    @NotNull
    String authId;

    @Column
    @NotNull
    @Size(min = 5, max = 30)
    String name;

    @Column
    @NotNull
    @Email(message = "Please enter valid email")
    String email;

    @Column
    @NotNull
    @Size(min = 5, max = 100)
    String currentAddress;

    @Column
    @NotNull
    @Size(min = 5, max = 100)
    String presentWorkField;

    @Column
    @NotNull
    @Size(min = 5, max = 100)
    String phoneNo;

    Boolean isActive;

    Boolean isEmailVerified;


}
