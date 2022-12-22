package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exceptions.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@Slf4j
public class ServiceAdvisor {
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;

    public ServiceAdvisor(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

//    @Before(value = "execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..))&&args(username,projectId)", argNames = "username,projectId")
//    public void serviceAuthorization(String username, int projectId){
//        log.debug("Before the service method, username {}, projectID {}",username,projectId);
//        Project project = projectDAO.findById(projectId).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
//    }
//
//
//    @Before(value = "execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..))&&args(projectDTO)",argNames = "projectDTO")
//    public void serviceAuthorization(ProjectDTO projectDTO){
//        log.debug("Before the service method");
//        Project projectEntity = projectDAO.findById(projectDTO.getId()).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if(!projectEntity.getUsername().matches(projectDTO.getUsername())) throw new AccessDeniedException();
//    }

    @Pointcut("execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.createNewProject())")
    public void serviceAuthorization(){

    }

    @Before(value = "serviceAuthorization() &&args(username,projectId)", argNames = "username,projectId")
    public void serviceAuthorization(String username, int projectId){
//        log.debug("Before the service method, username {}, projectID {}",username,projectId);
//        Project project = projectDAO.findById(projectId).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
        executeAdvice(username,projectId);
    }


    @Before(value = "serviceAuthorization() &&args(projectDTO)",argNames = "projectDTO")
    public void serviceAuthorization(ProjectDTO projectDTO){
        if (projectDTO.getId()!=null)executeAdvice(projectDTO.getUsername(),projectDTO.getId());
//        log.debug("Before the service method");
//        Project projectEntity = projectDAO.findById(projectDTO.getId()).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if(!projectEntity.getUsername().matches(projectDTO.getUsername())) throw new AccessDeniedException();
    }


    @Before(value = "serviceAuthorization() && args(username,task, ..)", argNames = "username,task")
    private void serviceAuthorization(String username, TaskDTO task){
        executeAdvice(username, task.getProjectId());
        if (task.getId()!=null){
            Task taskEntity = taskDAO.findById(task.getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
            if (taskDAO.findAllTaskByProjectId(taskEntity.getProjectId()).stream().noneMatch(t-> t.getId()== task.getId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

    }
    private void executeAdvice(String username, int projectId){
        log.debug("Before the service method");
        Project projectEntity = projectDAO.findById(projectId).orElseThrow(()->new EmptyResultDataAccessException(1));
        if(!projectEntity.getUsername().matches(username)) throw new AccessDeniedException();
    }
}
