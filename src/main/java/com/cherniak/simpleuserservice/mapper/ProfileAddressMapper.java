package com.cherniak.simpleuserservice.mapper;

import com.cherniak.simpleuserservice.dto.ProfileAddressDto;
import com.cherniak.simpleuserservice.model.ProfileAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProfileAddressMapper {

    @Mapping(target = "profileId", source = "profile.id")
    ProfileAddressDto toDto(ProfileAddress address);

    @Mapping(target = "profile", ignore = true)
    ProfileAddress toEntity(ProfileAddressDto dto);

    List<ProfileAddressDto> toDtoList(Set<ProfileAddress> addresses);

    Set<ProfileAddress> toEntityList(List<ProfileAddressDto> dtos);
}
