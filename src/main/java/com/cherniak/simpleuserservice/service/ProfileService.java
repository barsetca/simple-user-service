package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.ProfileDto;

public interface ProfileService {

    ProfileDto getById(Long id);

    ProfileDto getByUserId(Long userId);

    void create(ProfileDto dto, String username);

    ProfileDto update(ProfileDto dto, String username);
}
