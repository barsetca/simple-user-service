package com.cherniak.simpleuserservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private @NotNull Long userId;
    private @NotNull String firstName;
    private @NotNull String lastName;
    private String phoneNumber;
    private List<ProfileAddressDto> addresses;
}
