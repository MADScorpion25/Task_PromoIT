package com.dealerapp.controllers;

import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Admin;
import com.dealerapp.models.Client;
import com.dealerapp.models.Dealer;
import com.dealerapp.models.User;
import com.dealerapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/info/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/admins")
    public List<UserDto> getAdmins(){
        return userService.getAdmins();
    }

    @GetMapping("/dealers")
    public List<UserDto> getDealers(){
        return userService.getDealers();
    }

    @GetMapping("/clients")
    public List<UserDto> getClients(){
        return userService.getClients();
    }

    @PostMapping("/new")
    public ResponseEntity<User> createUser(@RequestBody UserDto user){
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit/{id}")
    public User updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @GetMapping("/roles")
    public List<String> getUserRoles(){
        return userService.getUserRoles();
    }
}
