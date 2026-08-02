package ai.prama.empmanagement.dto;

import ai.prama.empmanagement.enums.AuditAction;

import java.time.LocalDateTime;

public class AuditLogDto {

    public record Response(
        Long id,
        Long actorId,
        String actorName,
        Long departmentId,
        String departmentName,
        Long projectId,
        String projectName,
        Long roleId,
        String roleName,
        AuditAction action,
        String description,
        LocalDateTime createdAt
    ) {}
}
