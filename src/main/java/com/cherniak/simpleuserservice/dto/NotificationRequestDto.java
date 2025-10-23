package com.cherniak.simpleuserservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NotificationRequestDto(Long id, @NotNull String title, @NotNull String message, Long senderId,
                                     @NotNull List<Long> recipientIds) {
}
