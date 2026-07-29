package ai.prama.empmanagement.controller;


import ai.prama.empmanagement.dto.UserDto;
import ai.prama.empmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@NullMarked
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a user and assigns them to a department and role")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input — validation error or duplicate email"),
        @ApiResponse(responseCode = "404", description = "Department or role not found")
    })
    public ResponseEntity<UserDto.Response> createNewUser(@Valid @RequestBody UserDto.CreateRequest user) {
        log.info("Creating new user with email: {}", user.email());
        UserDto.Response response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Partially updates user fields by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input — validation error or duplicate email"),
        @ApiResponse(responseCode = "404", description = "User, department, or role not found")
    })
    public ResponseEntity<UserDto.Response> updateUser(@PathVariable long id, @Valid @RequestBody UserDto.UpdateRequest user) {
        log.info("Updating user with id: {}", id);
        UserDto.Response response = userService.updateUser(id, user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        log.info("Deleting user with id: {}", id);
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user's details by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto.Response> getUserById(@PathVariable long id) {
        log.info("Fetching user with id: {}", id);
        UserDto.Response response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    public ResponseEntity<List<UserDto.Response>> findAllUsers() {
        log.info("Fetching all users");
        List<UserDto.Response> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/department/{id}")
    @Operation(summary = "Find users by department", description = "Retrieves all users belonging to the specified department")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<List<UserDto.Response>> findUsersByDept(@PathVariable long id) {
        log.info("Fetching users by department id: {}", id);
        List<UserDto.Response> users = userService.getUsersByDepartment(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{id}")
    @Operation(summary = "Find users by role", description = "Retrieves all users assigned the specified role")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<List<UserDto.Response>> findUsersByRole(@PathVariable long id) {
        log.info("Fetching users by role id: {}", id);
        List<UserDto.Response> users = userService.getUsersByRole(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/project/{id}")
    @Operation(summary = "Find users by project", description = "Retrieves all users assigned to the specified project")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<List<UserDto.Response>> findUsersByProject(@PathVariable long id) {
        log.info("Fetching users by project id: {}", id);
        List<UserDto.Response> users = userService.getUsersByProject(id);
        return ResponseEntity.ok(users);
    }

}
