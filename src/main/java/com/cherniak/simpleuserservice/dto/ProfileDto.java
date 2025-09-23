package com.cherniak.simpleuserservice.dto;

import java.util.List;

public record ProfileDto(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        List<ProfileAddressDto> addresses
) {
}
