package com.cherniak.simpleuserservice.controller;

import com.cherniak.simpleuserservice.dto.AuthRequest;
import com.cherniak.simpleuserservice.dto.AuthResponse;
import com.cherniak.simpleuserservice.dto.RegisterRequest;
import com.cherniak.simpleuserservice.dto.UserDto;
import com.cherniak.simpleuserservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authUser(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }
}
