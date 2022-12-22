package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exceptions.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional  // ascpect: transaction management
public class UserServiceImpl implements UserService {


    private UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;
    private Transformer transformer;

    public UserServiceImpl(UserDAO userDAO, ProjectDAO projectDAO, TaskDAO taskDAO, Transformer transformer) {
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.save(transformer.toUser(userDTO));


//        if (true) throw new RuntimeException();
//        userDAO.save(new User("dsff","dsfsdf","dsfsdfsd"));



    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO userDTO = userDAO.findById(username).map(transformer::toUserDTO).orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(userDTO.getPassword())){
            return userDTO;
        }
        throw new AuthenticationException();
    }

    @Override
    public UserDTO getUserAccountDetails(String username) {
        UserDTO userDTO = userDAO.findById(username).map(transformer::toUserDTO).get();
        return userDTO;
    }

    @Override
    public void updateUserAccountDetails(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.update(transformer.toUser(userDTO));
    }

    @Override
    public void deleteUserAccount(String username) {
        List<Project> projectList = projectDAO.findAllProjectsByUserName(username);
        for (Project project : projectList) {
            List<Task> taskList = taskDAO.findAllTaskByProjectId(project.getId());
            taskList.forEach(task -> taskDAO.deleteById(task.getId()));
            projectDAO.deleteById(project.getId());
        }
        userDAO.deleteById(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userDAO.findById(username).map(transformer::toUserDTO).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
