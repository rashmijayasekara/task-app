package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Transactional  // ascpect: transaction management
public class UserServiceImpl implements UserService {


    private UserDAO userDAO;
    private Transformer transformer;

    public UserServiceImpl(UserDAO userDAO, Transformer transformer) {
        this.userDAO = userDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {

        userDAO.save(transformer.toUser(userDTO));
//        if (true) throw new RuntimeException();
        userDAO.save(new User("dsff","dsfsdf","dsfsdfsd"));



    }
}
