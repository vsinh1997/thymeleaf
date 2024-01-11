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
        Optional<User> userOptional = Optional.of(getUserById(id));
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
        Optional<User> userOptional = Optional.of(getUserById(id));
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
    public void addUser(UserRequest userDto) {
        validateEmailNotExist(userDto.getEmail());

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
        Optional<User> exisitingUser = Optional.of(getUserById(id));
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
    public Page<User> findPaginated(int pageNumber, int pageSize, String email, String sortBy, String sortDir) {
        Pageable pageable = createPageable(pageNumber, pageSize, sortBy, sortDir);

        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCase(email, pageable);
        }
        return userRepository.findAll(pageable);

    }

    private Pageable createPageable(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort.Direction direction = getDirection(sortDir);
        String sortedBy = getSortBy(sortBy);

        return PageRequest.of(pageNumber - 1, pageSize, direction, sortedBy);
    }

    private Sort.Direction getDirection(String sortDir) {
        return (sortDir == null || sortDir.isEmpty() || sortDir.equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
    }

    private String getSortBy(String sortBy) {
        return (sortBy == null || sortBy.isEmpty() || sortBy.equalsIgnoreCase("id")) ? "id" : sortBy;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.of(getUserById(id));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with this id " + id + " not found"));
    }

    private void validateEmailNotExist(String email) {
        List<User> userList = userRepository.findAll();
        if (userList.stream().anyMatch(user -> user.getEmail().equals(email))) {
            throw new NotFoundException("This email " + email + " is existing, please try with another email");
        }
    }


}
