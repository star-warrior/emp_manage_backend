package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.AuditLogDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {
    AuditLogDto.Response createAuditLog(AuditLogDto.CreateRequest request);
    AuditLogDto.Response getAuditLogById(Long id);
    List<AuditLogDto.Response> getAuditLogsByEmployee(Long employeeId);
    List<AuditLogDto.Response> getAuditLogsByDepartment(Long departmentId);
    List<AuditLogDto.Response> getAuditLogsByProject(Long projectId);
    List<AuditLogDto.Response> getAuditLogsByAction(String action);
    List<AuditLogDto.Response> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end);
}
