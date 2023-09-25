package com.codeVirtus.demo.controllers;

import com.codeVirtus.demo.Dto.LoginDto;
import com.codeVirtus.demo.Dto.RegisterDto;
import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.requests.AuthResponse;
import com.codeVirtus.demo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Data
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return  userService.register(registerDto);
    }

    @PostMapping("/process_register")
    public ResponseEntity<String> processRegister(@RequestBody RegisterDto registerDto, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return  userService.processRegister(registerDto, getSiteURL(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        return userService.verify(code);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    }
