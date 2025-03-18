package edu.ntnu.idatt2105.calculator.controller;


import edu.ntnu.idatt2105.calculator.dto.UserCreationDTO;
import edu.ntnu.idatt2105.calculator.dto.UserDTO;
import edu.ntnu.idatt2105.calculator.service.AuthenticationService;
import edu.ntnu.idatt2105.calculator.security.AuthenticationRequest;
import edu.ntnu.idatt2105.calculator.security.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthenticationService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserCreationDTO userDTO) {
        System.out.println(userDTO.getUsername() + " " + userDTO.getPassword() + " " + userDTO.getEmail());
        logger.info("User " + userDTO.getUsername() + " is trying to register");
        try {
            AuthenticationResponse authResponse = authService.register(userDTO);
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            logger.error("Failed to register user " + userDTO.getUsername());
            return ResponseEntity.internalServerError().build();
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDTO userDTO) {
        logger.info("User " + userDTO.getUsername() + " is trying to log in");
        try {
            AuthenticationResponse authResponse = authService.authenticate(AuthenticationRequest
                .builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build());
            logger.info("User " + userDTO.getUsername() + " has logged in");
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            logger.error("Failed to log in user " + userDTO.getUsername());
            return ResponseEntity.internalServerError().build();
        }
    }
}
