package com.cherniak.simpleuserservice.dto;

import com.cherniak.simpleuserservice.model.enums.NotificationState;

public record UserNotificationDto(Long recipientId, NotificationState state) {
}
