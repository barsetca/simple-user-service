package com.cherniak.simpleuserservice.service;

import com.cherniak.simpleuserservice.dto.UserDto;
import com.cherniak.simpleuserservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User createUser(User user);

    boolean existsByUsername(String username);

    boolean existByEmail(String email);

    UserDto getByUsername(String username);

    UserDto getById(Long id);

    Page<UserDto> getPage(Pageable pageable);
}
