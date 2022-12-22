package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class ProjectTaskServiceImpl implements ProjectTaskService {
    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return null;
    }

    @Override
    public ProjectDTO getProjectDetails(String username, Integer projectId) {
        return null;
    }

    @Override
    public void renameProject(ProjectDTO projectDTO) {

    }

    @Override
    public void deleteProject(String username, int projectId) {

    }
}
