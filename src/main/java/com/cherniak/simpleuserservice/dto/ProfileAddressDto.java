package com.cherniak.simpleuserservice.dto;

import com.cherniak.simpleuserservice.model.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileAddressDto(
        Long id,
        @NotNull Long profileId,
        @NotBlank String country,
        @NotBlank String city,
        String street,
        String zipCode,
        AddressType type
) {
}