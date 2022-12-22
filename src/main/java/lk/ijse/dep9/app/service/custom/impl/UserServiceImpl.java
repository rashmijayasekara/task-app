package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.exceptions.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
