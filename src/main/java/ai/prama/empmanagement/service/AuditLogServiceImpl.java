package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.AuditLogDto;
import ai.prama.empmanagement.entity.AuditLog;
import ai.prama.empmanagement.entity.Department;
import ai.prama.empmanagement.entity.Projects;
import ai.prama.empmanagement.entity.Role;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.enums.AuditAction;
import ai.prama.empmanagement.exception.custom.ResourceNotFoundException;
import ai.prama.empmanagement.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public AuditLogDto.Response record(AuditAction action, User actor, Department department, Projects project, Role role, String description) {
        AuditLog auditLog = new AuditLog();
        auditLog.setActor(actor);
        auditLog.setDepartment(department);
        auditLog.setProject(project);
        auditLog.setRole(role);
        auditLog.setAction(action);
        auditLog.setDescription(description);

        auditLogRepository.save(auditLog);
        return toResponse(auditLog);
    }

    @Transactional(readOnly = true)
    public AuditLogDto.Response getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog not found with id " + id));
        return toResponse(auditLog);
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByActor(Long actorId) {
        return auditLogRepository.findByActorId(actorId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAllAuditLogs() {
        return auditLogRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByDepartment(Long departmentId) {
        return auditLogRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByProject(Long projectId) {
        return auditLogRepository.findByProjectId(projectId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByRole(Long roleId) {
        return auditLogRepository.findByRoleId(roleId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByAction(AuditAction action) {
        return auditLogRepository.findByAction(action).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto.Response> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByCreatedAtBetween(start, end).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AuditLogDto.Response toResponse(AuditLog auditLog) {
        return new AuditLogDto.Response(
                auditLog.getId(),
                auditLog.getActor().getId(),
                auditLog.getActor().getName(),
                auditLog.getDepartment() != null ? auditLog.getDepartment().getId() : null,
                auditLog.getDepartment() != null ? auditLog.getDepartment().getDepartmentName() : null,
                auditLog.getProject() != null ? auditLog.getProject().getId() : null,
                auditLog.getProject() != null ? auditLog.getProject().getName() : null,
                auditLog.getRole() != null ? auditLog.getRole().getId() : null,
                auditLog.getRole() != null ? auditLog.getRole().getRoleName().name() : null,
                auditLog.getAction(),
                auditLog.getDescription(),
                auditLog.getCreatedAt()
        );
    }
}
