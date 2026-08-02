package ai.prama.empmanagement.exception.custom;

public class InvalidResetTokenException extends RuntimeException {

    public InvalidResetTokenException(String msg) {
        super(msg);
    }
}
