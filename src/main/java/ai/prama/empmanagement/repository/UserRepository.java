package ai.prama.empmanagement.repository;

import ai.prama.empmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByActive(boolean active);
    List<User> findByDepartmentId(Long departmentId);
    List<User> findByRoles_Id(Long roleId);
    List<User> findByProjects_Id(Long projectId);
}
