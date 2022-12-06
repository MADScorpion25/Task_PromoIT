package com.dealerapp.dto;

import com.dealerapp.models.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
    @Email(message = "Please, enter correct email.")
    private String login;
    @NotBlank(message = "Password cannot be empty")
    @Min(value = 8)
    private String password;
    private UserRole role;
}
