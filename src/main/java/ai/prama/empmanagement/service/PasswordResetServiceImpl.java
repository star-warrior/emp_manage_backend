package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.PasswordResetDto;
import ai.prama.empmanagement.entity.PasswordResetToken;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.enums.AuditAction;
import ai.prama.empmanagement.exception.custom.InvalidResetTokenException;
import ai.prama.empmanagement.repository.PasswordResetTokenRepository;
import ai.prama.empmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final long TOKEN_EXPIRY_MINUTES = 15;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Transactional
    public void requestPasswordReset(PasswordResetDto.ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.email()).orElse(null);
        if (user == null) {
            return;
        }

        tokenRepository.deleteByEmployeeId(user.getId());

        PasswordResetToken token = new PasswordResetToken();
        token.setEmployee(user);
        token.setToken(UUID.randomUUID());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));
        tokenRepository.save(token);

        String resetUrl = frontendUrl + "/reset-password?token=" + token.getToken();

        String body = """
            <h1>Password Reset</h1>
            <p>You requested a password reset. Use the following token to reset your password within 15 minutes:</p>
                <p>
                        <a href="%s"
                           style="
                                display:inline-block;
                                padding:12px 24px;
                                background:#2563eb;
                                color:white;
                                text-decoration:none;
                                border-radius:6px;
                                font-weight:bold;">
                            Reset Password
                        </a>
                    </p>
            <p>If you did not request this, you can safely ignore this email.</p>
                    <p>If the button doesn't work, copy and paste this link into your browser:</p>
                    <p>%s</p>
            """.formatted(resetUrl,resetUrl);

        emailService.sendMail(user.getEmail(), "Password Reset", body);
    }

    @Transactional
    public void resetPassword(PasswordResetDto.ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.token())
                .orElseThrow(() -> new InvalidResetTokenException("Invalid reset token"));

        if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidResetTokenException("Reset token has expired or already been used");
        }

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        User user = resetToken.getEmployee();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        auditLogService.record(AuditAction.PASSWORD_RESET, user, user.getDepartment(), null, user.getRole(),
                "Password reset for user " + user.getName() + " (" + user.getEmail() + ")");

        tokenRepository.deleteByEmployeeId(user.getId());
    }
}
