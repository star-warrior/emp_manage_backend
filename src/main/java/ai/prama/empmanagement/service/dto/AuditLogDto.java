package ai.prama.empmanagement.service.dto;

import java.time.LocalDateTime;

public class AuditLogDto {

    public record CreateRequest(
        Long employeeId,
        Long departmentId,
        Long projectId,
        String action,
        String description
    ) {}

    public record Response(
        Long id,
        Long employeeId,
        String employeeName,
        Long departmentId,
        String departmentName,
        Long projectId,
        String projectName,
        String action,
        String description,
        LocalDateTime createdAt
    ) {}
}
