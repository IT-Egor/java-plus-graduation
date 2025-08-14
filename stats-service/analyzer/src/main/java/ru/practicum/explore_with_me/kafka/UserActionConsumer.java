package ru.practicum.explore_with_me.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;
import ru.practicum.explore_with_me.kafka.mapper.KafkaPropertiesMapper;

@Component
public class UserActionConsumer extends KafkaConsumer<Long, UserActionAvro> {
    public UserActionConsumer(KafkaConfig config) {
        super(KafkaPropertiesMapper.mapToProperties(config.getConsumer().getUserAction().getProperties()));
    }
}
