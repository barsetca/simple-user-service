package com.cherniak.simpleuserservice.controller;

import com.cherniak.simpleuserservice.dto.NotificationRequestDto;
import com.cherniak.simpleuserservice.dto.NotificationResponseDto;
import com.cherniak.simpleuserservice.service.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid NotificationRequestDto dto) {
        notificationService.create(dto);
        return ResponseEntity.ok("Notification created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(notificationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NotificationResponseDto>> getAllByUser(Pageable pageable, Principal principal) {
        return ResponseEntity.ok(notificationService.getPageByUser(pageable, principal.getName()));
    }
}
