package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.user.CreateUserRequest;
import ru.practicum.explore_with_me.dto.user.UserResponse;
import ru.practicum.explore_with_me.model.User;

@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User requestToUser(CreateUserRequest createUserRequest);

    UserResponse userToResponse(User user);
}
