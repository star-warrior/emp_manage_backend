package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.dto.DepartmentDto;
import ai.prama.empmanagement.service.DepartmentService;
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

@Tag(name = "Department", description = "Department management APIs")
@RestController
@RequestMapping("/v1/department")
@RequiredArgsConstructor
@NullMarked
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "Create a new department", description = "Adds a new department")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Department created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input — validation error or duplicate name")
    })
    public ResponseEntity<DepartmentDto.Response> createDepartment(@Valid @RequestBody DepartmentDto.CreateRequest request) {
        log.info("Creating new department: {}", request.name());
        DepartmentDto.Response response = departmentService.addDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Retrieves a department's details by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department found"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<DepartmentDto.Response> getDepartmentById(@PathVariable long id) {
        log.info("Fetching department with id: {}", id);
        DepartmentDto.Response response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments")
    @ApiResponse(responseCode = "200", description = "List of departments retrieved successfully")
    public ResponseEntity<List<DepartmentDto.Response>> getAllDepartments() {
        log.info("Fetching all departments");
        List<DepartmentDto.Response> responses = departmentService.getAllDepartments();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department", description = "Deletes a department by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<Void> deleteDepartment(@PathVariable long id) {
        log.info("Deleting department with id: {}", id);
        departmentService.removeDepartmentById(id);
        return ResponseEntity.noContent().build();
    }
}
