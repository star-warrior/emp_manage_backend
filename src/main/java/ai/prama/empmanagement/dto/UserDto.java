package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {

    public record CreateRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotNull(message = "Department is required")
        Long departmentId,
        @NotNull(message = "Role is required")
        Long roleId
    ) {}

    public record UpdateRequest(
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,
        @Email(message = "Email must be valid")
        String email,
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        Long departmentId,
        Long roleId
    ) {}

    public record Response(
        Long id,
        String name,
        String email,
        boolean active,
        String departmentName,
        String roleName,
        LocalDateTime createdAt
    ) {}
}
