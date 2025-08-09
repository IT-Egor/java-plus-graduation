package ru.practicum.explore_with_me.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.enums.request.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created", nullable = false)
    LocalDateTime created;

    @Column(name = "event_id", nullable = false)
    Long eventId;

    @Column(name = "requester_id", nullable = false)
    Long requesterId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RequestStatus status;
}