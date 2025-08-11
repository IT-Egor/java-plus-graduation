package ru.practicum.explore_with_me.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;

import java.util.Map;
import java.util.Properties;

@Component
public class ConfiguredKafkaProducer extends KafkaProducer<String, SpecificRecordBase> {
    private final String topic;

    public ConfiguredKafkaProducer(KafkaConfig config) {
        super(mapToProperties(config.getProducer().getProperties()));
        this.topic = config.getProducer().getTopic();
    }

    private static Properties mapToProperties(Map<String, String> configMap) {
        Properties properties = new Properties();
        properties.putAll(configMap);
        return properties;
    }

    public void send(EventSimilarityAvro similarity) {
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                topic,
                null,
                similarity.getTimestamp().toEpochMilli(),
                "%s_%s".formatted(similarity.getEventA(), similarity.getEventB()),
                similarity
        );
        super.send(record);
    }

    @Override
    public void close() {
        flush();
        super.close();
    }
}