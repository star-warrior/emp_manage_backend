package ai.prama.empmanagement.repository;

import ai.prama.empmanagement.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {

    Optional<Projects> findByName(String name);

    List<Projects> findByStatus(boolean status);
}
