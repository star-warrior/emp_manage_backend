package ai.prama.empmanagement.service;

import ai.prama.empmanagement.entity.AuditLog;
import ai.prama.empmanagement.entity.Department;
import ai.prama.empmanagement.entity.Projects;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.repository.AuditLogRepository;
import ai.prama.empmanagement.repository.DepartmentRepository;
import ai.prama.empmanagement.repository.ProjectsRepository;
import ai.prama.empmanagement.repository.UserRepository;
import ai.prama.empmanagement.service.dto.AuditLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectsRepository projectsRepository;

    @Transactional
    public AuditLogDto.Response createAuditLog(AuditLogDto.CreateRequest request) {
        User employee = userRepository.findById(request.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + request.employeeId()));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id " + request.departmentId()));
        Projects project = projectsRepository.findById(request.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + request.projectId()));

        AuditLog auditLog = new AuditLog();
        auditLog.setEmployee(employee);
        auditLog.setDepartment(department);
        auditLog.setProject(project);
        auditLog.setAction(request.action());
        auditLog.setDescription(request.description());

        auditLogRepository.save(auditLog);
        return toResponse(auditLog);
    }

    public AuditLogDto.Response getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AuditLog not found with id " + id));
        return toResponse(auditLog);
    }

    public List<AuditLogDto.Response> getAuditLogsByEmployee(Long employeeId) {
        return auditLogRepository.findByEmployeeId(employeeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogDto.Response> getAuditLogsByDepartment(Long departmentId) {
        return auditLogRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogDto.Response> getAuditLogsByProject(Long projectId) {
        return auditLogRepository.findByProjectId(projectId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogDto.Response> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogDto.Response> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByCreatedAtBetween(start, end).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AuditLogDto.Response toResponse(AuditLog auditLog) {
        return new AuditLogDto.Response(
                auditLog.getId(),
                auditLog.getEmployee().getId(),
                auditLog.getEmployee().getName(),
                auditLog.getDepartment().getId(),
                auditLog.getDepartment().getDepartmentName(),
                auditLog.getProject().getId(),
                auditLog.getProject().getName(),
                auditLog.getAction(),
                auditLog.getDescription(),
                auditLog.getCreatedAt()
        );
    }
}
