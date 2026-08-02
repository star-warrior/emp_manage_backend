package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.dto.AuthDto;
import ai.prama.empmanagement.dto.PasswordResetDto;
import ai.prama.empmanagement.enums.AuditAction;
import ai.prama.empmanagement.security.JwtService;
import ai.prama.empmanagement.security.UserPrincipal;
import ai.prama.empmanagement.service.AuditLogService;
import ai.prama.empmanagement.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@NullMarked
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordResetService passwordResetService;
    private final AuditLogService auditLogService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates with email and password, returns a JWT token")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    public ResponseEntity<AuthDto.AuthResponse> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        log.info("Login attempt for email: {}", request.email());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);
        String role = principal.getAuthorities().iterator().next().getAuthority();

        auditLogService.record(AuditAction.LOGIN, principal.getUser(), principal.getUser().getDepartment(), null,
                principal.getUser().getRole(), "User " + principal.getUser().getEmail() + " logged in");

        log.info("Login successful for email: {}", request.email());
        return ResponseEntity.ok(new AuthDto.AuthResponse(token, "Bearer", principal.getUsername(), role));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset", description = "Sends a password reset token to the given email if an account exists")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reset email sent"),
    })
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody PasswordResetDto.ForgotPasswordRequest request) {
        passwordResetService.requestPasswordReset(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Resets the password using a valid reset token")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Password reset successful"),
        @ApiResponse(responseCode = "400", description = "Invalid, expired or used token")
    })
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetDto.ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
