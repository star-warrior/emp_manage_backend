package ai.prama.empmanagement.repository;

import ai.prama.empmanagement.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByEmployeeId(Long employeeId);

    List<AuditLog> findByDepartmentId(Long departmentId);

    List<AuditLog> findByProjectId(Long projectId);

    List<AuditLog> findByAction(String action);

    List<AuditLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
