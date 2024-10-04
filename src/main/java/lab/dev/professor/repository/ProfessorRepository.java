package lab.dev.professor.repository;

import lab.dev.professor.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
