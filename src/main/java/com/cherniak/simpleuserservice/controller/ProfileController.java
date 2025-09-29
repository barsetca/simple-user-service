package com.cherniak.simpleuserservice.controller;

import com.cherniak.simpleuserservice.dto.ProfileDto;
import com.cherniak.simpleuserservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getByUserId(userId));
    }

    @PostMapping("/profile")
    public ResponseEntity<String> create(@RequestBody @Valid ProfileDto profileDto, Principal principal) {
        profileService.create(profileDto, principal.getName());
        return ResponseEntity.ok("Profile created successfully");
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileDto> update(@RequestBody @Valid ProfileDto profileDto, Principal principal) {
        return ResponseEntity.ok(profileService.update(profileDto, principal.getName()));
    }
}
