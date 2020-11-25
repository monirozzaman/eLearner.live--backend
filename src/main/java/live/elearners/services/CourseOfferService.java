package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.Offers;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.dto.request.CourseOfferAddRequest;
import live.elearners.dto.response.CourseOfferDetailsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseOfferService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;


    public ResponseEntity<String> addOfferInCourse(String courseId, CourseOfferAddRequest courseOfferAddRequest) {
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                return new ResponseEntity("Course Not Found", HttpStatus.NOT_FOUND);
            }
            Course course = courseOptional.get();
            Offers offers = course.getOffer();
            offers.setBasicOfferDetails(courseOfferAddRequest.getBasicOfferDetails());
            offers.setBasicOfferEnable(courseOfferAddRequest.isBasicOfferEnable());
            offers.setBasicOfferInPercentage(courseOfferAddRequest.getBasicOfferInPercentage());
            offers.setSpecialOfferDetails(courseOfferAddRequest.getSpecialOfferDetails());
            offers.setSpecialOfferEndDate(courseOfferAddRequest.getSpecialOfferEndDate());
            offers.setSpecialOfferIncrementAfterTimeDuration(courseOfferAddRequest.getSpecialOfferIncrementAfterTimeDuration());
            offers.setSpecialOfferIncrementPercentage(courseOfferAddRequest.getSpecialOfferIncrementPercentage());
            offers.setSpecialOfferInHighestPercentage(courseOfferAddRequest.getSpecialOfferInHighestPercentage());
            offers.setSpecialOfferInLowestPercentage(courseOfferAddRequest.getSpecialOfferInLowestPercentage());
            offers.setSpecialOfferStatDate(courseOfferAddRequest.getSpecialOfferStatDate());
            course.setOffer(offers);
            courseRepository.save(course);
            return new ResponseEntity("Offer added", HttpStatus.OK);

        } else {
            return new ResponseEntity("Access deny", HttpStatus.FORBIDDEN);
        }
    }

    public CourseOfferDetailsResponse getCoursePriceWithCurrentOffer(String courseId) {
        CourseOfferDetailsResponse courseOfferDetailsResponse = new CourseOfferDetailsResponse();
        courseOfferDetailsResponse.setOfferPrice(10000);
        courseOfferDetailsResponse.setOfferFinishDateAndTime("12-12-12");
        courseOfferDetailsResponse.setOfferReason("Covid 29");

        return courseOfferDetailsResponse;

    }
}
