package live.elearners.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class PreRegistration {
    String preRegistrationId;
    String registeredCourseId;
    String registeredCourseName;
    String registeredCourseType;
    String name;
    String phoneNo;
    String interestLevel;
    String email;
    String address;
    String orientationDateTime;
}
