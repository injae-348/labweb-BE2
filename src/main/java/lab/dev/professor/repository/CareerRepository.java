package lab.dev.professor.repository;

import lab.dev.professor.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
