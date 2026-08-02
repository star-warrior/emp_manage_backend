package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.dto.AuditLogDto;
import ai.prama.empmanagement.enums.AuditAction;
import ai.prama.empmanagement.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping("/all")
    @Operation(summary = "Get all audit logs", description = "Retrieves all audit log entries")
    @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAllAuditLogs() {
        log.info("Fetching all audit logs");
        List<AuditLogDto.Response> responses = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/actor/{actorId}")
    @Operation(summary = "Find audit logs by actor", description = "Retrieves all audit log entries performed by an actor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByActor(@PathVariable long actorId) {
        log.info("Fetching audit logs for actor id: {}", actorId);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByActor(actorId);
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

    @GetMapping("/role/{roleId}")
    @Operation(summary = "Find audit logs by role", description = "Retrieves all audit log entries for a role")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByRole(@PathVariable long roleId) {
        log.info("Fetching audit logs for role id: {}", roleId);
        List<AuditLogDto.Response> responses = auditLogService.getAuditLogsByRole(roleId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = "action")
    @Operation(summary = "Find audit logs by action", description = "Retrieves all audit log entries matching an action type")
    @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AuditLogDto.Response>> getAuditLogsByAction(@RequestParam AuditAction action) {
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
