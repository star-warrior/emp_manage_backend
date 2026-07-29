package ai.prama.empmanagement.service;

import ai.prama.empmanagement.entity.Projects;
import ai.prama.empmanagement.repository.ProjectsRepository;
import ai.prama.empmanagement.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectsRepository projectsRepository;

    @Transactional
    public ProjectDto.Response createProject(ProjectDto.CreateRequest request) {
        if (projectsRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("Project " + request.name() + " already exists");
        }

        Projects project = new Projects();
        project.setName(request.name());
        project.setStatus(request.status());
        project.setDescription(request.description());

        projectsRepository.save(project);
        return toResponse(project);
    }

    @Transactional
    public ProjectDto.Response updateProject(Long id, ProjectDto.UpdateRequest request) {
        Projects project = projectsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + id));

        if (request.name() != null) project.setName(request.name());
        if (request.status() != null) project.setStatus(request.status());
        if (request.description() != null) project.setDescription(request.description());

        projectsRepository.save(project);
        return toResponse(project);
    }

    @Transactional
    public void removeProject(Long id) {
        Projects project = projectsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + id));
        projectsRepository.delete(project);
    }

    public ProjectDto.Response getProjectById(Long id) {
        Projects project = projectsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + id));
        return toResponse(project);
    }

    public List<ProjectDto.Response> getAllProjects() {
        return projectsRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectDto.Response> getProjectsByStatus(boolean status) {
        return projectsRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProjectDto.Response toResponse(Projects project) {
        return new ProjectDto.Response(
                project.getId(),
                project.getName(),
                project.isStatus(),
                project.getDescription(),
                project.getCreated_at(),
                project.getUpdated_at()
        );
    }
}
