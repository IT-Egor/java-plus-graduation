package ru.practicum.explore_with_me.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.explore_with_me.config.KafkaConfig;
import ru.practicum.explore_with_me.handler.UserActionHandler;
import ru.practicum.explore_with_me.kafka.UserActionConsumer;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionListener implements Runnable {
    private final UserActionConsumer consumer;
    private final KafkaConfig kafkaConfig;
    private final UserActionHandler userActionHandler;

    @Override
    public void run() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            consumer.subscribe(kafkaConfig.getConsumer().getUserAction().getTopics());
            Duration pollTimeout = kafkaConfig.getConsumer().getUserAction().getPollTimeout();

            while (true) {
                ConsumerRecords<Long, UserActionAvro> records = consumer.poll(pollTimeout);

                if (!records.isEmpty()) {
                    log.info("Received {} records: {}", records.count(), records);

                    for (ConsumerRecord<Long, UserActionAvro> record : records) {
                        UserActionAvro userActionAvro = record.value();
                        log.info("User action handling: {}", userActionAvro);
                        userActionHandler.handle(userActionAvro);
                        log.info("User action handled");
                    }

                    consumer.commitAsync();
                    log.info("Offset committed");
                }
            }
        } catch (WakeupException e) {
            log.error("WakeupException", e);
        } catch (Exception e) {
            log.error("User action handling error", e);
        } finally {
            try {
                consumer.commitAsync();
                log.info("Refreshed");
            } catch (Exception e) {
                log.error("Refreshing error", e);
            } finally {
                consumer.close();
                log.info("Consumer closed");
            }
        }
    }
}
