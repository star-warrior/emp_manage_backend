package ai.prama.empmanagement.service;

import ai.prama.empmanagement.entity.Department;
import ai.prama.empmanagement.entity.Role;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.repository.DepartmentRepository;
import ai.prama.empmanagement.repository.RoleRepository;
import ai.prama.empmanagement.repository.UserRepository;
import ai.prama.empmanagement.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.Response createUser(UserDto.CreateRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email " + request.email() + " already exists");
        }

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id " + request.departmentId()));

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id " + request.roleId()));

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setDepartment(department);
        user.setRole(role);
        user.setActive(true);

        userRepository.save(user);
        return toResponse(user);
    }

    @Transactional
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));

        if (request.name() != null) user.setName(request.name());
        if (request.email() != null) {
            userRepository.findByEmail(request.email()).ifPresent(existing -> {
                if (existing.getId() != id) {
                    throw new IllegalArgumentException("Email " + request.email() + " already exists");
                }
            });
            user.setEmail(request.email());
        }
        if (request.password() != null) user.setPassword(request.password());
        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with id " + request.departmentId()));
            user.setDepartment(department);
        }
        if (request.roleId() != null) {
            Role role = roleRepository.findById(request.roleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found with id " + request.roleId()));
            user.setRole(role);
        }

        userRepository.save(user);
        return toResponse(user);
    }

    @Transactional
    public void removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
        userRepository.delete(user);
    }

    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
        return toResponse(user);
    }

    public List<UserDto.Response> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<UserDto.Response> getUsersByDepartment(Long departmentId) {
        return userRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<UserDto.Response> getUsersByRole(Long roleId) {
        return userRepository.findByRoleId(roleId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<UserDto.Response> getUsersByProject(Long projectId) {
        return userRepository.findByProjects_Id(projectId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private UserDto.Response toResponse(User user) {
        return new UserDto.Response(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getDepartment().getDepartmentName(),
                user.getRole().getRoleName().name(),
                user.getCreated_at()
        );
    }
}
