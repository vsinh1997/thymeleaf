package com.example.thymeleaftutorial.implement;

import com.example.thymeleaftutorial.dto.UserRequest;
import com.example.thymeleaftutorial.dto.UserDto;
import com.example.thymeleaftutorial.entity.User;
import com.example.thymeleaftutorial.message.NotFoundException;
import com.example.thymeleaftutorial.repository.UserRepository;
import com.example.thymeleaftutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new NotFoundException("List null");
        }
        return userList.stream().map(
                user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .isDeleted(user.isDeleted())
                        .build()
        ).sorted(Comparator.comparing(UserDto::getId)).toList();
    }

    @Override
    public void recoverUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User Not Found");
        }
        User recoverUser = User.builder()
                .id(userOptional.get().getId())
                .email(userOptional.get().getEmail())
                .password(userOptional.get().getPassword())
                .lastName(userOptional.get().getLastName())
                .firstName(userOptional.get().getFirstName())
                .isDeleted(false)
                .build();

        userRepository.save(recoverUser);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User Not Found");
        }
        User recoverUser = User.builder()
                .id(userOptional.get().getId())
                .email(userOptional.get().getEmail())
                .password(userOptional.get().getPassword())
                .lastName(userOptional.get().getLastName())
                .firstName(userOptional.get().getFirstName())
                .isDeleted(true)
                .build();

        userRepository.save(recoverUser);
    }

    @Override
    public void addUSer(UserRequest userDto) {
        List<User> userList = userRepository.findAll();
        for (User u : userList) {
            if (u.getEmail().equals(userDto.getEmail())) {
                throw new NotFoundException("This email " + userDto.getEmail() + " is existing, please try with another email");
            }
        }
        User newUser = User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .build();

        userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserRequest user, Long id) {
        Optional<User> exisitingUser = userRepository.findById(id);
        if (exisitingUser.isEmpty()) {
            throw new NotFoundException("This user with id " + id + " is not existing");
        }

        User updateUser = User.builder()
                .id(id)
                .email(exisitingUser.get().getEmail())

                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();

        userRepository.save(updateUser);
    }

    @Override
    public Page<User> findPaginated(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findByUserEmail(String email) {
        List<User> users = userRepository.findUserByEmail(email);
        if (users.isEmpty()) {
            throw new NotFoundException("This user with email " + email + " is not existing");
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("This user with id " + id + " is not existing");
        }
        return userOptional;
    }

}
