package com.dealerapp.dto;

import com.dealerapp.models.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
    @Email
    private String login;
    @NotBlank
    private String password;
    private UserRole role;
}
