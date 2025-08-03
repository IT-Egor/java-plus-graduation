package ru.practicum.explore_with_me.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.client.StatsClient;

@Configuration
public class AppConfig {
    @Bean
    public StatsClient getStatsClient(@Value("${app.stats-server.uri}") String address) {
        return new StatsClient(address);
    }
}
