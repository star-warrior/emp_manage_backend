package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DepartmentDto {

    public record CreateRequest(
        @NotBlank(message = "Department name is required")
        String name
    ) {}

    public record UpdateRequest(
        @Size(max = 100, message = "Name must be at most 100 characters")
        @NotNull
        String name
    ) {}

    public record Response(Long id, String name) {}
}
