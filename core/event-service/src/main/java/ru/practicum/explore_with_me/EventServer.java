package ru.practicum.explore_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.practicum.explore_with_me.exception.controller.ErrorHandler;
import ru.practicum.explore_with_me.feign.StatsFeign;

@SpringBootApplication(scanBasePackageClasses = {
        EventServer.class,
        ErrorHandler.class
})
@EnableFeignClients(basePackageClasses = StatsFeign.class)
public class EventServer {
    public static void main(String[] args) {
        SpringApplication.run(EventServer.class, args);
    }
}
