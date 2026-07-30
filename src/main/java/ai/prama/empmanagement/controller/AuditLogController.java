package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.dto.AuditLogDto;
import ai.prama.empmanagement.service.AuditLogService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Audit Log", description = "Audit log management APIs")
@RestController
@RequestMapping("/v1/audit-log")
@RequiredArgsConstructor
@NullMarked
public class AuditLogController {
    private static final Logger log = LoggerFactory.getLogger(AuditLogController.class);

    private final AuditLogService auditLogService;

    @PostMapping
    @Operation(summary = "Create an audit log entry", description = "Records an audit log for an employee action")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Audit log created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Employee, department, or project not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditLogDto.Response> createAuditLog(@Valid @RequestBody AuditLogDto.CreateRequest request) {
        log.info("Creating audit log for employee id: {}", request.employeeId());
        AuditLogDto.Response response = auditLogService.createAuditLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get audit log by ID", description = "Retrieves a single audit log entry by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit log found"),
        @ApiResponse(responseCode = "404", description = "Audit log not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<AuditLogDto.Response> getAuditLogById(@PathVariable long id) {
        log.info("Fetching audit log with id: {}", id);
        AuditLogDto.Response response = auditLogService.getAuditLogById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Find audit logs by employee", description = "Retrieves all audit log entries for an employee")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByEmployee(@PathVariable long employeeId) {
        log.info("Fetching audit logs for employee id: {}", employeeId);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByEmployee(employeeId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Find audit logs by department", description = "Retrieves all audit log entries for a department")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByDepartment(@PathVariable long departmentId) {
        log.info("Fetching audit logs for department id: {}", departmentId);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByDepartment(departmentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Find audit logs by project", description = "Retrieves all audit log entries for a project")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByProject(@PathVariable long projectId) {
        log.info("Fetching audit logs for project id: {}", projectId);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByProject(projectId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = "action")
    @Operation(summary = "Find audit logs by action", description = "Retrieves all audit log entries matching an action type")
    @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByAction(@RequestParam String action) {
        log.info("Fetching audit logs with action: {}", action);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByAction(action);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = {"startDate", "endDate"})
    @Operation(summary = "Find audit logs by date range", description = "Retrieves all audit log entries within a date range")
    @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        log.info("Fetching audit logs from {} to {}", startDate, endDate);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }
}
