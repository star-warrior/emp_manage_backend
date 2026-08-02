package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.AuditLogDto;
import ai.prama.empmanagement.entity.Department;
import ai.prama.empmanagement.entity.Projects;
import ai.prama.empmanagement.entity.Role;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.enums.AuditAction;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {
    AuditLogDto.Response record(AuditAction action, User actor, Department department, Projects project, Role role, String description);
    AuditLogDto.Response getAuditLogById(Long id);
    List<AuditLogDto.Response> getAllAuditLogs();
    List<AuditLogDto.Response> getAuditLogsByActor(Long actorId);
    List<AuditLogDto.Response> getAuditLogsByDepartment(Long departmentId);
    List<AuditLogDto.Response> getAuditLogsByProject(Long projectId);
    List<AuditLogDto.Response> getAuditLogsByRole(Long roleId);
    List<AuditLogDto.Response> getAuditLogsByAction(AuditAction action);
    List<AuditLogDto.Response> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end);
}
