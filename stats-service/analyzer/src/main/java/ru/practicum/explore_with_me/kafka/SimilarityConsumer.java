package ru.practicum.explore_with_me.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;
import ru.practicum.explore_with_me.kafka.mapper.KafkaPropertiesMapper;

@Component
public class SimilarityConsumer extends KafkaConsumer<String, EventSimilarityAvro> {
    public SimilarityConsumer(KafkaConfig config) {
        super(KafkaPropertiesMapper.mapToProperties(config.getConsumer().getSimilarity().getProperties()));
    }
}
