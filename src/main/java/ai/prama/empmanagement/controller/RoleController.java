package ai.prama.empmanagement.controller;

import ai.prama.empmanagement.dto.RoleDto;
import ai.prama.empmanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Role", description = "Role lookup APIs")
@RestController
@RequestMapping("/v1/role")
@RequiredArgsConstructor
@NullMarked
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @GetMapping("/all")
    @Operation(summary = "Get all roles", description = "Retrieves a list of all available roles")
    @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleDto.Response>> getAllRoles() {
        log.info("Fetching all roles");
        List<RoleDto.Response> responses = roleService.getAllRoles();
        return ResponseEntity.ok(responses);
    }
}
