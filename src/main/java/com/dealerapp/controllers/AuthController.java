package com.dealerapp.controllers;

import com.dealerapp.config.jwt.JwtUtils;
import com.dealerapp.dto.UserDto;
import com.dealerapp.pojo.JwtResponse;
import com.dealerapp.pojo.LoginRequest;
import com.dealerapp.pojo.MessageResponse;
import com.dealerapp.pojo.SignupRequest;
import com.dealerapp.security.UserDetailsImpl;
import com.dealerapp.services.UserService;
import com.dealerapp.validation.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final  JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity authUser(@RequestBody LoginRequest loginRequest){
        ArrayList<SimpleGrantedAuthority> strings = new ArrayList<>();

        strings.add(new SimpleGrantedAuthority("ADMIN"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword(),
                        strings
                        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles.get(0)));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody SignupRequest signupRequest) throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setLogin(signupRequest.getLogin());
        userDto.setPassword(signupRequest.getPassword());
        userService.register(userDto);

        return ResponseEntity.ok(new MessageResponse("User " + userDto.getLogin()+ " successfully created"));
    }
}
