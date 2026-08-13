package ai.prama.empmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LoginMethod {
    GOOGLE, MICROSOFT, LOCAL;

    @JsonCreator
    public static LoginMethod fromString(String value) {
         return valueOf(value.toUpperCase());
    }
}
