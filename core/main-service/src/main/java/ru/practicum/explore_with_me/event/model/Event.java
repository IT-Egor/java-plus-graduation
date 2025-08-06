package ru.practicum.explore_with_me.event.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.enums.event.EventState;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "initiator_id")
    Long initiatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    Location location;

    @Enumerated(EnumType.STRING)
    EventState state;

    LocalDateTime eventDate;

    @Column(length = 2000)
    String annotation;

    @Column(length = 7000)
    String description;

    String title;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    LocalDateTime createdOn;

    @Transient
    Integer confirmedRequests = 0;

    LocalDateTime publishedOn;

    @Transient
    Long views = 0L;
}