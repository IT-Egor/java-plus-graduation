package ru.practicum.explore_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.practicum.explore_with_me.exception.controller.ErrorHandler;

@EnableFeignClients
@SpringBootApplication(scanBasePackageClasses = {
        RequestServer.class,
        ErrorHandler.class
})
public class RequestServer {
    public static void main(String[] args) {
        SpringApplication.run(RequestServer.class, args);
    }
}
