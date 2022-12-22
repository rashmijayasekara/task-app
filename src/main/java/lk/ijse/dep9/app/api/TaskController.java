package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects/{projectId:\\d+}/tasks")
@CrossOrigin
public class TaskController {
    private ProjectTaskService taskService;

    public TaskController(ProjectTaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    public TaskDTO createNewTask(@RequestBody @Validated(ValidationGroups.Create.class) TaskDTO taskDTO, @RequestAttribute String username,@PathVariable int projectId){

        taskDTO.setProjectId(projectId);
        return taskService.createNewTask(username,taskDTO);
    }

    @PatchMapping(value = "/{taskId:\\d+}",consumes = "application/json")
    public void renameTask(@RequestBody @Validated(ValidationGroups.Update.class) TaskDTO task,@RequestAttribute String username,@PathVariable int projectId, @PathVariable int taskId){
        task.setProjectId(projectId);
        task.setId(taskId);
        taskService.renameTask(username,task);

    }
    @DeleteMapping(value = "/{taskId:\\d+}")
    public void deleteTask(@RequestAttribute String username,@PathVariable int projectId,@PathVariable int id){
        taskService.deleteTask(username,new TaskDTO(id,"",projectId));
    }
    @GetMapping(value = "/{taskId:\\d+}",produces = "application/json")
    public TaskDTO getTaskDetails(@RequestAttribute String username,@PathVariable int projectId,@PathVariable int id){
        taskService.getTaskDetails(username,new TaskDTO(id,"",projectId));
    }
    @GetMapping
    public void getAllTasks(@RequestAttribute String username,@PathVariable int projectId){
        taskService.getAllTasks(username,projectId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{taskId:\\d+}",params = "completed")
    public void updateTaskStatus(@RequestAttribute String username,@PathVariable int projectId, @PathVariable int taskId, @PathVariable boolean completed){
        taskService.updateTaskStatus(username,new TaskDTO(taskId,null,projectId),completed);
    }
}
