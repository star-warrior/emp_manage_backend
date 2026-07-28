package ai.prama.empmanagement.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Roles {
    EMPLOYEE, MANAGER, ADMIN;

    @JsonCreator
    public static Roles fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
