package ru.practicum.explore_with_me.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;

import java.util.Map;
import java.util.Properties;

@Component
public class SimilarityConsumer extends KafkaConsumer<String, EventSimilarityAvro> {
    public SimilarityConsumer(KafkaConfig config) {
        super(mapToProperties(config.getConsumer().getSimilarity().getProperties()));
    }

    private static Properties mapToProperties(Map<String, String> configMap) {
        Properties properties = new Properties();
        properties.putAll(configMap);
        return properties;
    }
}
