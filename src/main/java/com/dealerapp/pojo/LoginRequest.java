package com.dealerapp.pojo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @Email
    private String login;
    @NotBlank
    private String password;
}
