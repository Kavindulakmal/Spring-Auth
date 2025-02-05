package com.jwt.demo.service;

import com.jwt.demo.dto.LoginRequestDTO;
import com.jwt.demo.dto.LoginResponseDTO;
import com.jwt.demo.dto.RegisterRequestDTO;
import com.jwt.demo.dto.RegisterResponseDTO;
import com.jwt.demo.entity.UserEntity;
import com.jwt.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;



    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity createUser(RegisterRequestDTO userData){
        UserEntity newUser = new UserEntity(userData.getName(),
                userData.getEmail(),
                userData.getUsername(),
                passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(newUser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginData){
       // Boolean userPresent = isUserEnable(loginData.getUsername());
       // if(!userPresent) return new LoginResponseDTO(null,null,"User not found","not enter");



        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(),
                    loginData.getPassword()));

        } catch (Exception e){
            return new LoginResponseDTO(null,null,"User not found","Error");
        }

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("role","User");
        claims.put("email","company@gmail.com");

        String token = jwtService.getJWTToken(loginData.getUsername(),claims);

        System.out.println(jwtService.getFieldFromToken(token,"role"));

        return new LoginResponseDTO(token, LocalDateTime.now(),null,"token generated Success");

    }

    public RegisterResponseDTO register(RegisterRequestDTO req){
        if (isUserEnable(req.getUsername())) return new RegisterResponseDTO(null,"user already exists");

        var userData = this.createUser(req);
        if (userData.getId()== null) return new RegisterResponseDTO(null,"system error");

        return new RegisterResponseDTO(String.format("user registered at %s",userData.getId()),null);
    }

    private Boolean isUserEnable(String username){
        return userRepository.findByUsername(username).isPresent();
    }

}
