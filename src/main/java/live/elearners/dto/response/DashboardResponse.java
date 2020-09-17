package live.elearners.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardResponse {
    private int numberOfCategory;
    private int numberOfCourses;
    private int numberOfCategoryInstructors;
    private int numberOfLearners;
    private int numberOfAdmins;
    private int numberOfEngineers;
}
