package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.NotificationRequestDto;
import com.cherniak.simpleuserservice.dto.NotificationResponseDto;
import com.cherniak.simpleuserservice.dto.UserNotificationDto;
import com.cherniak.simpleuserservice.exception.EmptyListException;
import com.cherniak.simpleuserservice.exception.NotFoundException;
import com.cherniak.simpleuserservice.mapper.NotificationMapper;
import com.cherniak.simpleuserservice.mapper.UserNotificationMapper;
import com.cherniak.simpleuserservice.model.Notification;
import com.cherniak.simpleuserservice.model.enums.NotificationState;
import com.cherniak.simpleuserservice.model.enums.UserNotificationRole;
import com.cherniak.simpleuserservice.repository.NotificationRepository;
import com.cherniak.simpleuserservice.repository.UserNotificationRepository;
import com.cherniak.simpleuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final UserNotificationMapper userNotificationMapper;

    private final UserNotificationRepository userNotificationRepository;

    @Override
    public void create(NotificationRequestDto dto) {
        List<Long> recipientIds = dto.recipientIds();
        if (CollectionUtils.isEmpty(recipientIds)) {
            throw new EmptyListException("List of recipientIds must not be empty");
        }

        Notification notification = notificationMapper.toEntity(dto);
        Long senderId = dto.senderId();
        if (senderId == null) {
            notification.setRole(UserNotificationRole.SYSTEM);
        } else {
            notification.setSender(userRepository.getReferenceById(senderId));
            notification.setRole(UserNotificationRole.USER);
        }
        recipientIds.forEach(id -> {
                    if (!Objects.equals(id, senderId))
                        notification.addUserNotification(userRepository.getReferenceById(id));
                }
        );
        notificationRepository.save(notification);
    }

    @Override
    public NotificationResponseDto getById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found notification with id = " + id));
        NotificationResponseDto responseDto = notificationMapper.toDto(notification);
        List<UserNotificationDto> userNotificationDtos =
                notification.getUserNotifications()
                        .stream()
                        .map(userNotificationMapper::toDto)
                        .toList();
        responseDto.setRecipients(userNotificationDtos);
        return notificationMapper.toDto(notification);
    }

    @Override
    public void markRead(Long notificationId, String username) {
        int countModifiers = userNotificationRepository.setState(NotificationState.READ, Instant.now(), notificationId, username);
        System.out.println("/////////////////// countModifiers = " + countModifiers);
        if (countModifiers < 1) {
            throw new NotFoundException("Not found notification with id = " + notificationId);
        }
    }

    @Override
    public Page<NotificationResponseDto> getPageByUser(Pageable pageable, String username) {
        return notificationRepository.findAll(pageable, username).map(notification -> {
            NotificationResponseDto responseDto = notificationMapper.toDto(notification);
            List<UserNotificationDto> userNotificationDtos = notification.getUserNotifications().stream().map(userNotificationMapper::toDto).toList();
            responseDto.setRecipients(userNotificationDtos);
            return responseDto;
        });
    }
}
