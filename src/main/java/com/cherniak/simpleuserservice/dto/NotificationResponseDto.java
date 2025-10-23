package com.cherniak.simpleuserservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationResponseDto {
    @NotNull Long notificationId;
    Long senderId;
    @NotNull String title;
    @NotNull String message;
    @NotEmpty List<UserNotificationDto> recipients;
}
