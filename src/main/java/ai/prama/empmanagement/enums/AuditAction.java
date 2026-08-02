package ai.prama.empmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AuditAction {
    CREATE_USER, UPDATE_USER, DELETE_USER,
    CREATE_DEPARTMENT, UPDATE_DEPARTMENT, DELETE_DEPARTMENT,
    CREATE_PROJECT, UPDATE_PROJECT, DELETE_PROJECT,
    CREATE_ROLE, DELETE_ROLE,
    LOGIN, PASSWORD_RESET;

    @JsonCreator
    public static AuditAction fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
