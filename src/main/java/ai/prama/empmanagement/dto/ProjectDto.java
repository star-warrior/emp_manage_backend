package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ProjectDto {

    public record CreateRequest(
        @NotBlank(message = "Name is required")
        String name,
        boolean status,
        @Size(max = 500, message = "Description must be at most 500 characters")
        String description
    ) {}

    public record UpdateRequest(
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,
        Boolean status,
        @Size(max = 500, message = "Description must be at most 500 characters")
        String description
    ) {}

    public record Response(
        Long id,
        String name,
        boolean status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {}
}
