package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.AuthRequest;
import com.cherniak.simpleuserservice.dto.AuthResponse;
import com.cherniak.simpleuserservice.dto.RegisterRequest;
import com.cherniak.simpleuserservice.dto.UserDto;

public interface AuthService {
    AuthResponse authUser(AuthRequest authRequest);
    UserDto register (RegisterRequest registerRequest);
}
