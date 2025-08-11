package ru.practicum.explore_with_me;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;
import ru.practicum.explore_with_me.handler.UserActionHandler;
import ru.practicum.explore_with_me.kafka.ConfiguredKafkaConsumer;
import ru.practicum.explore_with_me.kafka.ConfiguredKafkaProducer;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorStarter implements CommandLineRunner {
    private final KafkaConfig kafkaConfig;
    private final ConfiguredKafkaProducer producer;
    private final ConfiguredKafkaConsumer consumer;
    private final UserActionHandler userActionHandler;

    @Override
    public void run(String... args) {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            consumer.subscribe(kafkaConfig.getConsumer().getTopics());
            Duration pollTimeout = kafkaConfig.getConsumer().getPollTimeout();

            while (true) {
                ConsumerRecords<Long, UserActionAvro> records = consumer.poll(pollTimeout);
                if (!records.isEmpty()) {
                    log.info("Received {} records: {}", records.count(), records);
                    for (ConsumerRecord<Long, UserActionAvro> record : records) {
                        UserActionAvro action = record.value();
                        log.info("User action handling: {}", action);
                        userActionHandler.handle(action)
                                .forEach(producer::send);
                        log.info("User action handled: {}", action);
                    }
                    log.info("Committing offsets");
                    consumer.commitAsync();
                }
            }
        } catch (WakeupException e) {
            log.error("WakeupException", e);
        } catch (Exception e) {
            log.error("User action handling error", e);
        } finally {
            try {
                producer.flush();
                consumer.commitAsync();
                log.info("Refreshed");
            } catch (Exception e) {
                log.error("Refreshing error", e);
            } finally {
                consumer.close();
                log.info("Consumer closed");
                producer.close();
                log.info("Producer closed");
            }
        }
    }
}
