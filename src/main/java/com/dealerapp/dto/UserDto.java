package com.dealerapp.dto;

import com.dealerapp.models.enums.UserRole;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
    @Email(message = "Please, enter correct email.")
    private String login;
    @NotBlank(message = "Password cannot be empty")
    @Length(min = 8, max = 30, message = "Password length must be from 8 to 30")
    private String password;
    private UserRole role;
}
