package ru.practicum.explore_with_me.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;

public interface KafkaClient {
    Producer<Long, SpecificRecordBase> getProducer();

    String getUserActionTopic();

    void stop();
}