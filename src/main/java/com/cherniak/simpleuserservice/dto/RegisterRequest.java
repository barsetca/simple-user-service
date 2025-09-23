package com.cherniak.simpleuserservice.dto;

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}
