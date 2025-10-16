package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.NotificationDto;
import com.cherniak.simpleuserservice.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientIds", ignore = true)
    NotificationDto toDto(Notification notification);

    Notification toEntity(NotificationDto dto);
}
