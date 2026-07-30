package ai.prama.empmanagement.service;

import ai.prama.empmanagement.entity.Department;
import ai.prama.empmanagement.exception.custom.DuplicateResourceException;
import ai.prama.empmanagement.exception.custom.ResourceNotFoundException;
import ai.prama.empmanagement.repository.DepartmentRepository;
import ai.prama.empmanagement.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentDto.Response addDepartment(DepartmentDto.CreateRequest request) {
        if (departmentRepository.findByDepartmentName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Department " + request.name() + " already exists");
        }

        Department newDept = new Department();
        newDept.setDepartmentName(request.name());
        departmentRepository.save(newDept);

        return toResponse(newDept);
    }

    @Transactional
    public DepartmentDto.Response updateDepartment(Long id, DepartmentDto.UpdateRequest request) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        if (request.name() != null) {
            if (departmentRepository.findByDepartmentName(request.name()).isPresent()) {
                throw new DuplicateResourceException("Department " + request.name() + " already exists");
            }
            dept.setDepartmentName(request.name());
        }
        departmentRepository.save(dept);
        return toResponse(dept);
    }

    @Transactional(readOnly = true)
    public DepartmentDto.Response getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        return toResponse(dept);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto.Response> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentRepository.delete(dept);
    }

    private DepartmentDto.Response toResponse(Department dept) {
        return new DepartmentDto.Response(dept.getId(), dept.getDepartmentName());
    }
}
