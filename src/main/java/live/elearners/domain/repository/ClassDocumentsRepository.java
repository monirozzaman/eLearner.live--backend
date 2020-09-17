package live.elearners.domain.repository;

import live.elearners.domain.model.ClassDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDocumentsRepository extends JpaRepository<ClassDocuments, String> {
}
