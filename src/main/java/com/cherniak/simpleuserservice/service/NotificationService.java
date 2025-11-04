package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.NotificationRequestDto;
import com.cherniak.simpleuserservice.dto.NotificationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    void create(NotificationRequestDto notification);

    NotificationResponseDto getById(Long id);

    void markRead(Long notificationId, String username);

    Page<NotificationResponseDto> getPageByUser(Pageable pageable, String username);
}
