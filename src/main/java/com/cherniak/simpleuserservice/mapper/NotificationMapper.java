package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.NotificationRequestDto;
import com.cherniak.simpleuserservice.dto.NotificationResponseDto;
import com.cherniak.simpleuserservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "notificationId", source = "id")
    @Mapping(target = "recipients", ignore = true)
    NotificationResponseDto toDto(Notification notification);

    Notification toEntity(NotificationRequestDto dto);
}
