package lab.dev.professor.repository;

import lab.dev.professor.domain.ResearchPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ResearchPageRepository extends JpaRepository<ResearchPage, Long> {
}
