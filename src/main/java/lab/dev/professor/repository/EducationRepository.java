package lab.dev.professor.repository;

import lab.dev.professor.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
