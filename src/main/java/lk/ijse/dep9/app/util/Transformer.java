package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    @Autowired
    private ModelMapper modelMapper;

    public User toUser(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }
    public UserDTO fromUser(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}