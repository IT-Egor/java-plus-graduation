package ru.practicum.explore_with_me.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.exception.model.NotFoundException;
import ru.practicum.explore_with_me.dao.UserRepository;
import ru.practicum.explore_with_me.dto.user.CreateUserRequest;
import ru.practicum.explore_with_me.dto.user.UserResponse;
import ru.practicum.explore_with_me.mapper.UserMapper;
import ru.practicum.explore_with_me.model.User;
import ru.practicum.explore_with_me.service.UserService;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.requestToUser(createUserRequest);
        UserResponse userResponse = userMapper.userToResponse(userRepository.save(user));
        log.info("User with id={} was created", userResponse.getId());
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserResponse> getUsers(List<Long> userIds, int from, int size) {
        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);

        Page<User> page;
        if (userIds != null && !userIds.isEmpty()) {
            page = userRepository.findUsersByIdIn(userIds, pageable);
        } else {
            page = userRepository.findAll(pageable);
        }

        log.info("Get users with {ids, from, size} = ({}, {}, {})", userIds, from, size);
        return page.getContent().stream().map(userMapper::userToResponse).toList();
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.deleteUserById(userId).isPresent()) {
            log.info("User with id={} was deleted", userId);
        } else {
            throw new NotFoundException(String.format("User with id=%d not found", userId));
        }
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.info("Get user with id = {}", userId);
        return userMapper.userToResponse(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id `%d` not found", userId))));
    }
}
