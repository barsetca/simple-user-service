package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    void create(NotificationDto notification);

    NotificationDto getById(Long id);

    Page<NotificationDto> getPageByUser(Pageable pageable, String username);
}
