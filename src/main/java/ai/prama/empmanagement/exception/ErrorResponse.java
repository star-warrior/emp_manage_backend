package ai.prama.empmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
    private String msg;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
