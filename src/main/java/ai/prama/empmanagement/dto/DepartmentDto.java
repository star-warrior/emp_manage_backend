package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentDto {

    public record CreateRequest(
        @NotBlank(message = "Department name is required")
        String name
    ) {}

    public record Response(Long id, String name) {}
}
