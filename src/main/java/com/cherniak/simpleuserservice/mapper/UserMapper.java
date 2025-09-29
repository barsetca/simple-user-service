package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.RegisterRequest;
import com.cherniak.simpleuserservice.dto.UserDto;
import com.cherniak.simpleuserservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterRequest registerRequest);
}
