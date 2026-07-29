package ai.prama.empmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDto {

    public record LoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password is required")
        String password
    ) {}

    public record AuthResponse(
        String token,
        String tokenType,
        String email,
        String role
    ) {}
}
