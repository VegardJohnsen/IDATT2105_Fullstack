package edu.ntnu.idatt2105.calculator.mapper;

import edu.ntnu.idatt2105.calculator.dto.UserCreationDTO;
import edu.ntnu.idatt2105.calculator.dto.UserDTO;
import org.springframework.stereotype.Component;
import edu.ntnu.idatt2105.calculator.model.User;

@Component
public class UserMapper {

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(),userDTO.getEmail());
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail());
    }   
}
