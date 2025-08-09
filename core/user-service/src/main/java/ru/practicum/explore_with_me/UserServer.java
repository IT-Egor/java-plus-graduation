package ru.practicum.explore_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.explore_with_me.exception.controller.ErrorHandler;

@SpringBootApplication(scanBasePackageClasses = {
        UserServer.class,
        ErrorHandler.class
})
public class UserServer {
    public static void main(String[] args) {
        SpringApplication.run(UserServer.class, args);
    }
}
