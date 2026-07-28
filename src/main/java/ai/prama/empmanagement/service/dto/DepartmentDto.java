package ai.prama.empmanagement.service.dto;

public class DepartmentDto {

    public record CreateRequest(String name) {}

    public record Response(Long id, String name) {}
}
