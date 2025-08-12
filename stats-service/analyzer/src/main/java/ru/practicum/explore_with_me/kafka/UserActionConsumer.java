package ru.practicum.explore_with_me.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;

import java.util.Map;
import java.util.Properties;

@Component
public class UserActionConsumer extends KafkaConsumer<Long, UserActionAvro> {
    public UserActionConsumer(KafkaConfig config) {
        super(mapToProperties(config.getConsumer().getUserAction().getProperties()));
    }

    private static Properties mapToProperties(Map<String, String> configMap) {
        Properties properties = new Properties();
        properties.putAll(configMap);
        return properties;
    }
}
