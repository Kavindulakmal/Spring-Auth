package com.jwt.demo.controller;

import com.jwt.demo.service.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final JWTService jwtService;

    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public String getHello(){
        return "Hello";
    }

    @PostMapping("/login")
    public String login(){
        return null;
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token){
      return jwtService.getUsername(token);
    }

}
