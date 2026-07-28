package ai.prama.empmanagement.service.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDto {

    public record CreateRequest(
        String name,
        String email,
        String password,
        Long departmentId,
        Set<Long> roleIds
    ) {}

    public record UpdateRequest(
        String name,
        String email,
        String password,
        Long departmentId,
        Set<Long> roleIds
    ) {}

    public record Response(
        Long id,
        String name,
        String email,
        boolean active,
        String departmentName,
        Set<String> roleNames,
        LocalDateTime createdAt
    ) {}
}
