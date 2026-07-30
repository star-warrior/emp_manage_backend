package ai.prama.empmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Roles {
    ADMIN, MANAGER, EMPLOYEE;

    @JsonCreator
    public static Roles fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
