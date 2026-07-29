package ai.prama.empmanagement.dto;

public class RoleDto {

    public record CreateRequest(String roleName) {}

    public record Response(Long id, String roleName) {}
}
