package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String addUser(UserDTO userDTO);

    LoginResponse loginUser(LoginDTO loginDTO);
}
