package ai.prama.empmanagement.service;

import ai.prama.empmanagement.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto.Response createProject(ProjectDto.CreateRequest request);
    ProjectDto.Response updateProject(Long id, ProjectDto.UpdateRequest request);
    void removeProject(Long id);
    ProjectDto.Response getProjectById(Long id);
    List<ProjectDto.Response> getAllProjects();
    List<ProjectDto.Response> getProjectsByStatus(boolean status);
}
