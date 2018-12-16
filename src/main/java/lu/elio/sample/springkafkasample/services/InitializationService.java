package lu.elio.sample.springkafkasample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationService.class);

    @Autowired
    private DynamicKafkaService dynamicKafkaService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        dynamicKafkaService.registerConsumer("topic1", "group-1", (message) -> {
            LOGGER.info("Received message for topic1: '{}'", message);
        });
        dynamicKafkaService.registerConsumer("topic2", "group-2", (message) -> {
            LOGGER.info("Received message for topic2: '{}'", message);
        });
        dynamicKafkaService.registerConsumer("topic3", "group-3", (message) -> {
            LOGGER.info("Received message for topic3: '{}'", message);
        });

        LOGGER.info("Sending simple message to Kafka topics");
        dynamicKafkaService.sendMessage("topic1", "Hi Welcome to Spring 1.1 For Apache Kafka");
        dynamicKafkaService.sendMessage("topic2", "Hi Welcome to Spring 2.1 For Apache Kafka");
        dynamicKafkaService.sendMessage("topic2", "Hi Welcome to Spring 2.2 For Apache Kafka");
        dynamicKafkaService.sendMessage("topic1", "Hi Welcome to Spring 1.2 For Apache Kafka");
        dynamicKafkaService.sendMessage("topic3", "Hi Welcome to Spring 3.1 For Apache Kafka");
    }

}