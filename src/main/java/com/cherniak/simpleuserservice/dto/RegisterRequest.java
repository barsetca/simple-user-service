package com.cherniak.simpleuserservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 8, max = 100)
        String password,
        @NotBlank
        @Email
        String email
) {
}
