package com.dealerapp.pojo;

import com.dealerapp.models.enums.UserRole;

public class JwtResponse {
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
