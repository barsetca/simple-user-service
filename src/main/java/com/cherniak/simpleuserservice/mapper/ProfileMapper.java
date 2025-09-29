package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.ProfileDto;
import com.cherniak.simpleuserservice.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfileAddressMapper.class})
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    ProfileDto toDto(Profile profile);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "profileAddresses", ignore = true)
    Profile toEntity(ProfileDto dto);
}
