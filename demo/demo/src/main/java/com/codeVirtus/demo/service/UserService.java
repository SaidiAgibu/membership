package com.codeVirtus.demo.service;

import com.codeVirtus.demo.Dto.LoginDto;
import com.codeVirtus.demo.Dto.RegisterDto;
import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.requests.AuthRequest;
import com.codeVirtus.demo.requests.AuthResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    public ResponseEntity<String> register(RegisterDto registerDto);

    public ResponseEntity<String> processRegister(RegisterDto registerDto, String siteURL) throws MessagingException, UnsupportedEncodingException;
    public void sendVerificationEmail(User user, String siteURL)throws MessagingException, UnsupportedEncodingException;
    public ResponseEntity<String> verify(String verificationCode);

    public ResponseEntity<AuthResponse> login(LoginDto loginDto);
}
