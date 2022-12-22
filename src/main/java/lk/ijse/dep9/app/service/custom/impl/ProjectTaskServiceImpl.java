package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.exceptions.AccessDeniedException;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.Transformer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Component

public class ProjectTaskServiceImpl implements ProjectTaskService {
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    private Transformer transformer;

    public ProjectTaskServiceImpl(ProjectDAO projectDAO, TaskDAO taskDAO, Transformer transformer) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.transformer = transformer;
    }

    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return transformer.toProjectDTO(projectDAO.save(transformer.toProject(projectDTO)));
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return projectDAO.findAllProjectsByUserName(username).stream().map(transformer::toProjectDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectDetails(String username, int projectId) {
        return projectDAO.findById(projectId).map(transformer::toProjectDTO).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
//        return project;
    }

    @Override
    public void renameProject(ProjectDTO projectDTO) {
//        Project projectEntity = projectDAO.findById(projectDTO.getId()).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if(!projectEntity.getUsername().matches(projectDTO.getUsername())) throw new AccessDeniedException();
        projectDAO.update(transformer.toProject(projectDTO));

    }

    @Override
    public void deleteProject(String username, int projectId) {
//        Project project = projectDAO.findById(projectId).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
        taskDAO.findAllTaskByProjectId(projectId).forEach(task -> taskDAO.deleteById(task.getId()));
        projectDAO.deleteById(projectId);
    }

    @Override
    public TaskDTO createNewTask(String username, TaskDTO taskDTO) {

        return null;
    }

    @Override
    public void renameTask(String username, TaskDTO task) {

    }

    @Override
    public void deleteTask(String username, TaskDTO taskDTO) {

    }

    @Override
    public TaskDTO getTaskDetails(String username, TaskDTO taskDTO) {
        return null;
    }

    @Override
    public void getAllTasks(String username, int projectId) {

    }

    @Override
    public void updateTaskStatus(String username, TaskDTO taskDTO, boolean completed) {

    }
}
