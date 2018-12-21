package lu.elio.sample.samplespringrabbitmq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQInitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQInitializationService.class);

    @Autowired
    private DynamicRabbitMQService dynamicRabbitMQService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        dynamicRabbitMQService.registerConsumer("exchange1", "route-1", (message) -> {
            LOGGER.info("route-1: Received message for exchange1: '{}'", message);
        });
        dynamicRabbitMQService.registerConsumer("exchange2", "route-2", (message) -> {
            LOGGER.info("route-2: Received message for exchange2: '{}'", message);
        });
        dynamicRabbitMQService.registerConsumer("exchange3", "route-3", (message) -> {
            LOGGER.info("route-3: Received message for exchange3: '{}'", message);
        });

        LOGGER.info("Sending simple message to RabbitMQ exchanges");
        dynamicRabbitMQService.sendMessage("exchange1", "Hi Welcome to Spring 1.1 For Apache Kafka");
        dynamicRabbitMQService.sendMessage("exchange2", "Hi Welcome to Spring 2.1 For Apache Kafka");
        dynamicRabbitMQService.sendMessage("exchange2", "Hi Welcome to Spring 2.2 For Apache Kafka");
        dynamicRabbitMQService.sendMessage("exchange1", "Hi Welcome to Spring 1.2 For Apache Kafka");
        dynamicRabbitMQService.sendMessage("exchange3", "Hi Welcome to Spring 3.1 For Apache Kafka");

        dynamicRabbitMQService.registerConsumer("exchange3", "route-4", (message) -> {
            LOGGER.info("route-4: Received message for exchange3: '{}'", message);
        });
    }

}
