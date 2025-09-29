package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.ProfileAddressDto;
import com.cherniak.simpleuserservice.dto.ProfileDto;
import com.cherniak.simpleuserservice.dto.UserInfo;
import com.cherniak.simpleuserservice.exception.AlreadyExistsException;
import com.cherniak.simpleuserservice.exception.NotFoundException;
import com.cherniak.simpleuserservice.mapper.ProfileAddressMapper;
import com.cherniak.simpleuserservice.mapper.ProfileMapper;
import com.cherniak.simpleuserservice.model.Profile;
import com.cherniak.simpleuserservice.model.ProfileAddress;
import com.cherniak.simpleuserservice.model.User;
import com.cherniak.simpleuserservice.repository.ProfileRepository;
import com.cherniak.simpleuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final ProfileAddressMapper addressMapper;

    @Override
    public ProfileDto getById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Not found profile with id = " + id));
    }

    @Override
    public ProfileDto getByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Not found profile for userId = " + userId));
        ProfileDto dto = profileMapper.toDto(profile);
        Set<ProfileAddress> addresses = profile.getProfileAddresses();
        if (!CollectionUtils.isEmpty(addresses)) {
            var addressDtos = addressMapper.toDtoList(addresses);
            dto.setAddresses(addressDtos);
        }
        return dto;
    }

    @Override
    @Transactional
    public void create(ProfileDto dto, String username) {
        Long userId = dto.getUserId();
        if (profileRepository.existsByUserId(userId)) {
            throw new AlreadyExistsException("Profile already exist for userId = " + userId);
        }
        UserInfo userInfo = userRepository.findUserInfoById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with userId = " + userId));

        if (!userInfo.getUsername().equals(username)) {
            throw new SecurityException("You can only create your own profile");
        }

        User user = userRepository.getReferenceById(userId);
        Profile toSave = profileMapper.toEntity(dto);
        toSave.setUser(user);
        List<ProfileAddressDto> addresses = dto.getAddresses();
        if (!CollectionUtils.isEmpty(addresses)) {
            addressMapper.toEntityList(addresses).forEach(toSave::addAddress);
        }
        profileRepository.save(toSave);
    }

    @Override
    public ProfileDto update(ProfileDto dto, String username) {
        Long id = dto.getId();
        if (id == null || !profileRepository.existsById(id)) {
            throw new NotFoundException("Not found profile id = " + id);
        }
        Long userId = dto.getUserId();
        UserInfo userInfo = userRepository.findUserInfoById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with userId = " + userId));

        if (!userInfo.getUsername().equals(username)) {
            throw new SecurityException("You can only create your own profile");
        }
        Profile profile = profileMapper.toEntity(dto);
        User user = userRepository.getReferenceById(userId);
        profile.setUser(user);
        return profileMapper.toDto(profileRepository.save(profile));
    }
}
