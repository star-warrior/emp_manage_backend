package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.converter.StatusConverter;
import ai.prama.empmanagement.dto.ProjectDto;
import ai.prama.empmanagement.service.ProjectService;
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

@Tag(name = "Projects", description = "Project management APIs")
@RestController
@RequestMapping("/v1/project")
@RequiredArgsConstructor
@NullMarked
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;
    private final StatusConverter statusConverter;

    @PostMapping
    @Operation(summary = "Create a new project", description = "Creates a project with a name, status, and description")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Project created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input — validation error or duplicate name")
    })
    public ResponseEntity<ProjectDto.Response> createNewProject(@Valid @RequestBody ProjectDto.CreateRequest project) {
        log.info("Creating new project: {}", project.name());
        ProjectDto.Response response = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing project", description = "Partially updates project fields by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Project updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ProjectDto.Response> updateProject(@PathVariable long id, @Valid @RequestBody ProjectDto.UpdateRequest project) {
        log.info("Updating project with id: {}", id);
        ProjectDto.Response response = projectService.updateProject(id, project);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project", description = "Deletes a project by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<Void> deleteProject(@PathVariable long id) {
        log.info("Deleting project with id: {}", id);
        projectService.removeProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID", description = "Retrieves a project's details by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Project found"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ProjectDto.Response> findProjectById(@PathVariable long id) {
        log.info("Fetching project with id: {}", id);
        ProjectDto.Response response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all projects", description = "Retrieves a list of all projects")
    @ApiResponse(responseCode = "200", description = "List of projects retrieved successfully")
    public ResponseEntity<List<ProjectDto.Response>> listAllProjects() {
        log.info("Fetching all projects");
        List<ProjectDto.Response> responses = projectService.getAllProjects();
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    @Operation(summary = "Find projects by status", description = "Retrieves projects filtered by active/inactive status")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status value (must be 'active' or 'inactive')")
    })
    public ResponseEntity<List<ProjectDto.Response>> listProjectStatus(@RequestParam("status") String status) {
        log.info("Fetching projects with status: {}", status);
        List<ProjectDto.Response> responses = projectService.getProjectsByStatus(statusConverter.convert(status));
        return ResponseEntity.ok(responses);
    }

}
