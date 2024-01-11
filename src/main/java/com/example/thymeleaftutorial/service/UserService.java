package com.example.thymeleaftutorial.service;

import com.example.thymeleaftutorial.dto.UserDto;
import com.example.thymeleaftutorial.dto.UserRequest;
import com.example.thymeleaftutorial.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    void recoverUserById(Long id);

    void deleteUserById(Long id);

    void addUSer(UserRequest userDto);

    Optional<User> findUserById(Long id);

    void updateUser(UserRequest user, Long id);
    Page<User> findPaginated(int pageNumber, int pageSizer, String email);

}
