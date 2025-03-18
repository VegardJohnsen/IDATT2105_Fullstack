package edu.ntnu.idatt2105.calculator.service;


import edu.ntnu.idatt2105.calculator.dto.UserCreationDTO;
import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.repository.UserRepository;
import edu.ntnu.idatt2105.calculator.security.AuthenticationRequest;
import edu.ntnu.idatt2105.calculator.security.AuthenticationResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Transactional
    public AuthenticationResponse register(UserCreationDTO userCreateDTO) {
        User user = User
            .builder()
            .username(userCreateDTO.getUsername())
            .password(passwordEncoder.encode(userCreateDTO.getPassword()))
            .email(userCreateDTO.getEmail())
            .build();
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + user.getUsername());
        }
        userRepository.save(user);

        //logger.info("User registered successfully: {}", user.getUsername());

        String jwtToken = jwtService.generateToken(user);
        logger.info("Their JWT is: " + jwtToken);

        return AuthenticationResponse
            .builder()
            .token(jwtToken)
            .build();
        
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Authenticating user information for: " + request.getUsername());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));

        String jwtToken = jwtService.generateToken(user);
        logger.info("User " + request.getUsername() + " has been authenticated");

        return AuthenticationResponse
            .builder()
            .token(jwtToken)
            .build();
    }




    
}
