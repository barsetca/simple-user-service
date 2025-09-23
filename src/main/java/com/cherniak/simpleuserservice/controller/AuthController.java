package com.cherniak.simpleuserservice.controller;

import com.cherniak.simpleuserservice.config.jwt.JwtUtil;
import com.cherniak.simpleuserservice.dto.AuthRequest;
import com.cherniak.simpleuserservice.dto.AuthResponse;
import com.cherniak.simpleuserservice.dto.RegisterRequest;
import com.cherniak.simpleuserservice.dto.UserDto;
import com.cherniak.simpleuserservice.model.User;
import com.cherniak.simpleuserservice.model.enums.Role;
import com.cherniak.simpleuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        String jwt = jwtUtil.generateToken(authRequest.username(), authentication.getAuthorities());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.existByUsername(registerRequest.username())) {
            throw new RuntimeException("Username already exists");
        }
        User user = userService.createUser(fromRegisterRequest(registerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(user));
    }

    private User fromRegisterRequest(RegisterRequest registerRequest) {
        User user = new User();
        user.setPassword(registerRequest.password());
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setRoles(Set.of(Role.ROLE_USER));
        return user;
    }

    private UserDto toDto(User user) {
        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), roles);
    }
}
