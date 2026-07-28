package ai.prama.empmanagement.service;

import ai.prama.empmanagement.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto.Response createUser(UserDto.CreateRequest request);
    UserDto.Response updateUser(Long id, UserDto.UpdateRequest request);
    void removeUser(Long id);
    UserDto.Response getUserById(Long id);
    List<UserDto.Response> getAllUsers();
    List<UserDto.Response> getUsersByDepartment(Long departmentId);
    List<UserDto.Response> getUsersByRole(Long roleId);
    List<UserDto.Response> getUsersByProject(Long projectId);
}
