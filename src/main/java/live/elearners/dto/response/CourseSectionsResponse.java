package live.elearners.dto.response;

import live.elearners.domain.model.CourseSections;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseSectionsResponse {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<CourseSections> sections;
}
