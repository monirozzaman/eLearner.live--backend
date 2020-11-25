package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.ClassDocuments;
import live.elearners.domain.model.Course;
import live.elearners.domain.repository.ClassDocumentsRepository;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.ClassDocumentRequest;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseDocumentsService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final ClassDocumentsRepository classDocumentsRepository;
    private final LearnersRepository learnersRepository;
    private final CourseSectionsRepository courseSectionsRepository;
    private final FileStorageService fileStorageService;

    public ResponseEntity<ClassDocuments> addClassDocuments(String courseId, String classId, ClassDocumentRequest classDocumentRequest) {
        if (authUtil.getRole().equals("INSTRUCTOR")) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new ResourseNotFoundException("Course Not Found");
            } else {
                Course course = courseOptional.get();
                if (Integer.valueOf(courseId) <= Integer.valueOf(course.getCourseNumberOfClasses())) {
                    Optional<ClassDocuments> classDocumentsOptional = classDocumentsRepository.findById(classId);
                    if (!classDocumentsOptional.isPresent()) {
                        throw new ResourseNotFoundException("Class Not Found");
                    }
                    ClassDocuments classDocuments = classDocumentsOptional.get();
                    classDocuments.setClassId(classId);
                    classDocuments.setPptNote(classDocumentRequest.getPptNote());
                    classDocuments.setVideoNote(classDocumentRequest.getVideoNote());
                    classDocuments.setClassRating("5");
                    classDocumentsRepository.save(classDocuments);
                    return new ResponseEntity(classDocuments, HttpStatus.OK);

                } else {
                    ClassDocuments classDocuments = new ClassDocuments();
                    classDocuments.setClassId("Class Limitation Over");
                    return new ResponseEntity(new ClassDocuments(), HttpStatus.NOT_ACCEPTABLE);
                }
            }

        } else {
            ClassDocuments classDocuments = new ClassDocuments();
            classDocuments.setClassId("Access Deny");
            return new ResponseEntity(new ClassDocuments(), HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<ClassDocuments> getClassDocumentsById(String courseId, String classId) {

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new ResourseNotFoundException("Course Not Found");
        } else {
            Course course = courseOptional.get();
            if (Integer.valueOf(courseId) <= Integer.valueOf(course.getCourseNumberOfClasses())) {
                Optional<ClassDocuments> classDocumentsOptional = classDocumentsRepository.findById(classId);
                if (!classDocumentsOptional.isPresent()) {
                    throw new ResourseNotFoundException("Class Not Found");
                }
                ClassDocuments classDocuments = classDocumentsOptional.get();
                return new ResponseEntity(classDocuments, HttpStatus.OK);

            } else {
                ClassDocuments classDocuments = new ClassDocuments();
                classDocuments.setClassId("Class Limitation Over");
                return new ResponseEntity(new ClassDocuments(), HttpStatus.NOT_ACCEPTABLE);
            }
        }

    }
}
