package com.dealerapp.pojo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JwtResponse {
    private String token;
    @Setter(AccessLevel.PRIVATE)
    private String tokenType = "Bearer";
    private Long id;
    private String login;
    private String role;

    public JwtResponse(String token, Long id, String login, String role) {
        this.token = token;
        this.id = id;
        this.login = login;
        this.role = role;
    }
}
