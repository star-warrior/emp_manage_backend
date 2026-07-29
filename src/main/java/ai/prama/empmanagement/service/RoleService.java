package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto.Response addRole(RoleDto.CreateRequest request);
    void removeRole(Long id);
    RoleDto.Response getRoleById(Long id);
    RoleDto.Response getRoleByName(String roleName);
    List<RoleDto.Response> getAllRoles();
}
