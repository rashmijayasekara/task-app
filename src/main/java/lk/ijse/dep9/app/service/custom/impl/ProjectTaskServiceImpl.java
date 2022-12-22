package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Task;
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
    private ProjectRepository projectDAO;
    private TaskRepository taskDAO;
    private Transformer transformer;

    public ProjectTaskServiceImpl(ProjectRepository projectDAO, TaskRepository taskDAO, Transformer transformer) {
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
        projectDAO.save(transformer.toProject(projectDTO));

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

        return transformer.toTaskDTO(taskDAO.save(transformer.toTask(taskDTO)));
    }

    @Override
    public void renameTask(String username, TaskDTO task) {
        Task taskEntity = taskDAO.findById(task.getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        taskEntity.setContent(task.getContent());
        taskDAO.save(transformer.toTask(task));
    }

    @Override
    public void deleteTask(String username, TaskDTO taskDTO) {
        taskDAO.deleteById(taskDTO.getId());
    }

    @Override
    public TaskDTO getTaskDetails(String username, TaskDTO taskDTO) {
       return taskDAO.findById(taskDTO.getId()).map(transformer::toTaskDTO).get();
    }

    @Override
    public List<TaskDTO> getAllTasks(String username, int projectId) {
        return taskDAO.findAllTaskByProjectId(projectId).stream().map(transformer::toTaskDTO).collect(Collectors.toList());
    }

    @Override
    public void updateTaskStatus(String username, TaskDTO taskDTO, boolean completed) {
        Task task = transformer.toTask(taskDTO);
        task.setStatus(completed? Task.Status.COMPLETED:Task.Status.NOT_COMPLETED);
        taskDAO.save(task);
    }
}
