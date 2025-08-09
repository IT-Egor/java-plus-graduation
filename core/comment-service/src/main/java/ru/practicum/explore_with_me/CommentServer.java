package ru.practicum.explore_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.practicum.explore_with_me.exception.controller.ErrorHandler;

@EnableFeignClients
@SpringBootApplication(scanBasePackageClasses = {
        CommentServer.class,
        ErrorHandler.class
})
public class CommentServer {
    public static void main(String[] args) {
        SpringApplication.run(CommentServer.class, args);
    }
}
