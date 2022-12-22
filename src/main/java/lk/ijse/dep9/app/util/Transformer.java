package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    private final ModelMapper modelMapper;

    public Transformer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toUser(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }
//    public UserDTO fromUser(User user){
//        return modelMapper.map(user, UserDTO.class);
//    }

    public UserDTO toUserDTO(User user) {
        return modelMapper.map(user,UserDTO.class);
    }

    public Project toProject(ProjectDTO projectDTO){
        return modelMapper.map(projectDTO, Project.class);
    }
    public ProjectDTO toProjectDTO(Project project){
        return modelMapper.map(project,ProjectDTO.class);
    }

    public Task toTask(TaskDTO taskDTO){
        return modelMapper.map(taskDTO,Task.class);
    }
    public TaskDTO toTaskDTO(Task task){
        return modelMapper.map(task,TaskDTO.class);
    }
}
