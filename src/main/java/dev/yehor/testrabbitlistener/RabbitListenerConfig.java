package dev.yehor.testrabbitlistener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class RabbitListenerConfig {
    @Bean
    public Consumer<MessageEvent> handleMessageEvent() {
        return event -> {
            System.out.println("Message received: " + event.getMessage() + " " + event.getTitle());
        };
    }

    @Bean
    public Consumer<ErrorEvent> handleErrorEvent() {
        return event -> {
            System.out.println("Received error: " + event.getErrorMessage());
        };
    }
}
