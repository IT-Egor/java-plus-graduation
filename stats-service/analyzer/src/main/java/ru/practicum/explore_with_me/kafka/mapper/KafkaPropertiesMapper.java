package ru.practicum.explore_with_me.kafka.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaPropertiesMapper {
    public static Properties mapToProperties(Map<String, String> configMap) {
        Properties properties = new Properties();
        properties.putAll(configMap);
        return properties;
    }
}
