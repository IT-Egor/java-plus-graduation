package ru.practicum.explore_with_me.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.config.UserActionWeights;
import ru.practicum.explore_with_me.model.ActionType;

@Component
@RequiredArgsConstructor
public class UserActionWeightMapper {
    private final UserActionWeights weights;

    public Double mapToWeight(ActionType actionType) {
        return switch (actionType) {
            case VIEW -> weights.getView();
            case REGISTER -> weights.getRegister();
            case LIKE -> weights.getLike();
        };
    }
}
