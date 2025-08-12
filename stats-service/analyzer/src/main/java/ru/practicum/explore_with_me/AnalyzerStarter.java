package ru.practicum.explore_with_me;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.kafka.listener.SimilarityListener;
import ru.practicum.explore_with_me.kafka.listener.UserActionListener;

@Component
@RequiredArgsConstructor
public class AnalyzerStarter implements CommandLineRunner {
    private final UserActionListener userActionListener;
    private final SimilarityListener similarityListener;

    @Override
    public void run(String... args) {
        Thread userActionThread = new Thread(userActionListener);
        userActionThread.setName("UserActionListenerThread");
        userActionThread.start();
        similarityListener.run();
    }
}
