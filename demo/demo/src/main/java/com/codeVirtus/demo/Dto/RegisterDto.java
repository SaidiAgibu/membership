package com.codeVirtus.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String verificationCode;
    boolean enabled;
}
