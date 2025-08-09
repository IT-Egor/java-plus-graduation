package ru.practicum.explore_with_me.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dao.RequestRepository;
import ru.practicum.explore_with_me.dto.event.EventFullDto;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.explore_with_me.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.explore_with_me.dto.request.RequestDto;
import ru.practicum.explore_with_me.enums.event.EventState;
import ru.practicum.explore_with_me.enums.request.RequestStatus;
import ru.practicum.explore_with_me.exception.model.*;
import ru.practicum.explore_with_me.feign.EventFeign;
import ru.practicum.explore_with_me.feign.UserFeign;
import ru.practicum.explore_with_me.mapper.RequestMapper;
import ru.practicum.explore_with_me.model.Request;
import ru.practicum.explore_with_me.service.RequestService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserFeign userFeign;
    private final EventFeign eventFeign;
    private final RequestMapper requestMapper;

    @Override
    public Collection<RequestDto> getAllUserRequest(Long userId) {
        userFeign.getUserById(userId);
        Set<Request> requests = requestRepository.findAllByRequesterId(userId);
        log.info("GET requests by userId = {}",userId);
        return requests.stream().map(requestMapper::toRequestDto).toList();
    }

    @Override
    @Transactional
    public RequestDto createRequest(Long userId, Long eventId) {
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new DuplicateRequestException("Request can be only one");
        }
        EventFullDto event = eventFeign.findEventById(eventId);

        userFeign.getUserById(userId);

        RequestStatus status = RequestStatus.PENDING;

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotPublishedEventRequestException("Event must be published");
        }

        int requestsSize = requestRepository.findAllByEventId(eventId).size();

        if (event.getParticipantLimit() != 0 && requestsSize >= event.getParticipantLimit()) {
            throw new RequestLimitException("No more seats for the event");
        }

        if (event.getParticipantLimit() == 0) {
            status = RequestStatus.CONFIRMED;
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new InitiatorRequestException("Initiator can't submit a request for event");
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .requesterId(userId)
                .eventId(event.getId())
                .status(status)
                .build();
        log.info("POST request body = {}",request);
        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        userFeign.getUserById(userId);
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Request not found"));
        request.setStatus(RequestStatus.CANCELED);
        log.info("Cancel request by requestId = {} and userId = {}",requestId,userId);
        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public Collection<RequestDto> getRequests(Long userId, Long eventId) {
        return requestRepository.findAllByEventId(eventId).stream().map(requestMapper::toRequestDto).toList();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequest(Long userId, Long eventId,
                                                        EventRequestStatusUpdateRequest updateRequest) {
        EventFullDto event = eventFeign.getByUserIdAndEventId(userId, eventId);

        List<Request> requests = requestRepository.findAllByIdIn(updateRequest.getRequestIds());

        for (Request request : requests) {
            if (!request.getEventId().equals(eventId)) {
                throw new NotFoundException("Request with requestId = " + request.getId() + "does not match eventId = " + eventId);
            }
        }

        int confirmedCount = requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED).size();
        int size = updateRequest.getRequestIds().size();
        int confirmedSize = updateRequest.getStatus().equals(RequestStatus.CONFIRMED) ? size : 0;

        if (event.getParticipantLimit() != 0 && confirmedCount + confirmedSize > event.getParticipantLimit()) {
            throw new TooManyRequestsException("Event limit exceed");
        }

        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();

        for (Request request : requests) {
            if (updateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
                request.setStatus(RequestStatus.CONFIRMED);
                confirmedRequests.add(requestMapper.toRequestDto(request));
            } else if (updateRequest.getStatus().equals(RequestStatus.REJECTED)) {
                if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
                    throw new AlreadyConfirmedException("The request cannot be rejected if it is confirmed");
                }
                request.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(requestMapper.toRequestDto(request));
            }
        }

        requestRepository.saveAll(requests);

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    @Override
    public Map<Long, List<RequestDto>> getConfirmedRequests(List<Long> eventIds) {
        List<Request> confirmedRequestsByEventId = requestRepository.findAllByEventIdInAndStatus(eventIds,
                RequestStatus.CONFIRMED);
        return confirmedRequestsByEventId.stream()
                .map(requestMapper::toRequestDto)
                .collect(Collectors.groupingBy(RequestDto::getEvent));
    }
}
