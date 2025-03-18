package edu.ntnu.idatt2105.calculator.service;

import edu.ntnu.idatt2105.calculator.dto.UserDTO;
import edu.ntnu.idatt2105.calculator.exception.UserNotFoundException;
import edu.ntnu.idatt2105.calculator.mapper.UserMapper;
import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO findUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        user.ifPresent(Objects::requireNonNull);
        return userMapper.toUserDTO(user
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + userDTO.getUsername())));
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + username));
    }
}