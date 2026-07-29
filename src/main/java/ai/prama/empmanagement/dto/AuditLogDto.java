package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AuditLogDto {

    public record CreateRequest(
        @NotNull(message = "Employee is required")
        Long employeeId,
        @NotNull(message = "Department is required")
        Long departmentId,
        @NotNull(message = "Project is required")
        Long projectId,
        @NotBlank(message = "Action is required")
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
