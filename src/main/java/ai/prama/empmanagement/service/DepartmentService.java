package ai.prama.empmanagement.service;

import ai.prama.empmanagement.service.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto.Response addDepartment(DepartmentDto.CreateRequest request);
    DepartmentDto.Response getDepartmentById(Long id);
    List<DepartmentDto.Response> getAllDepartments();
    void removeDepartmentById(Long id);
}
