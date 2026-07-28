package ai.prama.empmanagement.service;

import ai.prama.empmanagement.entity.Role;
import ai.prama.empmanagement.entity.Roles;
import ai.prama.empmanagement.repository.RoleRepository;
import ai.prama.empmanagement.service.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleDto.Response addRole(RoleDto.CreateRequest request) {
        Roles roleEnum = Roles.fromString(request.roleName());

        if (roleRepository.findByRoleName(roleEnum).isPresent()) {
            throw new IllegalArgumentException("Role " + request.roleName() + " already exists");
        }

        Role newRole = new Role();
        newRole.setRoleName(roleEnum);
        roleRepository.save(newRole);

        return toResponse(newRole);
    }

    @Transactional
    public void removeRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    public RoleDto.Response getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id " + id));
        return toResponse(role);
    }

    public RoleDto.Response getRoleByName(String roleName) {
        Roles roleEnum = Roles.fromString(roleName);
        Role role = roleRepository.findByRoleName(roleEnum)
                .orElseThrow(() -> new IllegalArgumentException("Role " + roleName + " not found"));
        return toResponse(role);
    }

    public List<RoleDto.Response> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RoleDto.Response toResponse(Role role) {
        return new RoleDto.Response(role.getId(), role.getRoleName().name());
    }
}
