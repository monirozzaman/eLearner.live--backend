package live.elearners.domain.model;


import live.elearners.config.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Audited
@Table(name = "preRegistration")
public class PreRegistration extends Auditable<String> {
    @Id
    String preRegistrationId;
    String registeredCourseId;
    String registeredDateAndTime;
    String registeredCourseName;
    String registeredCourseSectionId;
    String name;
    String phoneNo;
    String email;
    String address;
    String orientationDateTime;
}
