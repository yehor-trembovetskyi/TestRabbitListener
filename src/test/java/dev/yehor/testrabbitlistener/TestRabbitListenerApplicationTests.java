package dev.yehor.testrabbitlistener;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@ActiveProfiles("integration")
@Import(TestChannelBinderConfiguration.class)
class TestRabbitListenerApplicationTests {

    @Test
    public void testMessageToRabbit() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(TestRabbitListenerApplication.class))
                .web(WebApplicationType.NONE)
                .run()) {

            InputDestination target = context.getBean(InputDestination.class);
            target.send(new GenericMessage<>("{\"message\": \"Good evening, madam\",\"title\": \"Mr. Doe\"}".getBytes(StandardCharsets.UTF_8)), "greeting.messages");
        }
    }

    @Test
    public void testErrorToRabbit() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(TestRabbitListenerApplication.class))
                .web(WebApplicationType.NONE)
                .run()) {

            InputDestination target = context.getBean(InputDestination.class);
            target.send(new GenericMessage<>("{\"errorMessage\": \"Status: OK\"}".getBytes(StandardCharsets.UTF_8)), "alert.messages");
        }
    }

    @Test
    public void testBothDestinations() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(TestRabbitListenerApplication.class))
                .web(WebApplicationType.NONE)
                .run()) {

            InputDestination target = context.getBean(InputDestination.class);
            target.send(new GenericMessage<>("{\"message\": \"Good evening, madam\",\"title\": \"Mr. Doe\"}".getBytes(StandardCharsets.UTF_8)), "greeting.messages");
            target.send(new GenericMessage<>("{\"errorMessage\": \"Status: OK\"}".getBytes(StandardCharsets.UTF_8)), "alert.messages");
        }
    }
}
