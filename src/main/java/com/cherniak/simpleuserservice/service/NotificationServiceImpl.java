package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.NotificationDto;
import com.cherniak.simpleuserservice.exception.EmptyListException;
import com.cherniak.simpleuserservice.exception.NotFoundException;
import com.cherniak.simpleuserservice.mapper.NotificationMapper;
import com.cherniak.simpleuserservice.model.Notification;
import com.cherniak.simpleuserservice.model.User;
import com.cherniak.simpleuserservice.model.enums.UserNotificationRole;
import com.cherniak.simpleuserservice.repository.NotificationRepository;
import com.cherniak.simpleuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void create(NotificationDto dto) {
        List<Long> recipientIds = dto.recipientIds();
        if (CollectionUtils.isEmpty(recipientIds)) {
            throw new EmptyListException("List of recipientIds must not be empty");
        }

        Notification notification = notificationMapper.toEntity(dto);
        Long senderId = dto.senderId();
        if (senderId == null) {
            notification.addUserNotification(null, UserNotificationRole.SYSTEM);
        } else {
            notification.setSender(userRepository.getReferenceById(senderId));
        }
        recipientIds.forEach(id -> {
                    if (!Objects.equals(id, senderId))
                        notification.addUserNotification(userRepository.getReferenceById(id), UserNotificationRole.RECIPIENT);
                }
        );
        notificationRepository.save(notification);
    }

    @Override
    public NotificationDto getById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found notification with id = " + id));
        return notificationMapper.toDto(notification);
    }

    @Override
    public Page<NotificationDto> getPageByUser(Pageable pageable, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not found user with username = " + username));
        return notificationRepository.findAll(pageable, user.getId()).map(notificationMapper::toDto);
    }
}
