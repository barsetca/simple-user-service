package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.UserNotificationDto;
import com.cherniak.simpleuserservice.model.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper {

    @Mapping(target = "recipientId", source = "recipient.id")
    UserNotificationDto toDto(UserNotification userNotification);
}
