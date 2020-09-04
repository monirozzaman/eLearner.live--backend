package live.elearners.domain.model;

import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Audited
public class CourseSections extends Auditable<String> {
    @Id
    String sectionId;
    String sectionName;
}
