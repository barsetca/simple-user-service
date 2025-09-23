package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.config.jwt.JwtUtil;
import com.cherniak.simpleuserservice.dto.AuthRequest;
import com.cherniak.simpleuserservice.dto.AuthResponse;
import com.cherniak.simpleuserservice.dto.RegisterRequest;
import com.cherniak.simpleuserservice.dto.UserDto;
import com.cherniak.simpleuserservice.exception.AlreadyExistsException;
import com.cherniak.simpleuserservice.mapper.UserMapper;
import com.cherniak.simpleuserservice.model.User;
import com.cherniak.simpleuserservice.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authUser(AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        String jwt = jwtUtil.generateToken(authRequest.username(), authentication.getAuthorities());
        return new AuthResponse(jwt);
    }

    @Override
    public UserDto register(RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.username())) {
            throw new AlreadyExistsException("Username already exists");
        }
        if (userService.existByEmail(registerRequest.email())) {
            throw new AlreadyExistsException("Email already exists");
        }
        User user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        return userMapper.toDto(userService.createUser(user));
    }
}
