package com.cherniak.simpleuserservice.dto;

import com.cherniak.simpleuserservice.model.enums.AddressType;

public record ProfileAddressDto(
        String country,
        String city,
        String street,
        String zipCode,
        AddressType type
) {
}