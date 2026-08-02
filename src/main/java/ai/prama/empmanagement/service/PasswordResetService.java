package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.PasswordResetDto;

import java.util.UUID;

public interface PasswordResetService {

    void requestPasswordReset(PasswordResetDto.ForgotPasswordRequest request);

    void resetPassword(PasswordResetDto.ResetPasswordRequest request);
}
