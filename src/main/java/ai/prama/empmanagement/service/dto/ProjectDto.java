package ai.prama.empmanagement.service.dto;

import java.time.LocalDateTime;

public class ProjectDto {

    public record CreateRequest(
        String name,
        boolean status,
        String description
    ) {}

    public record UpdateRequest(
        String name,
        Boolean status,
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
