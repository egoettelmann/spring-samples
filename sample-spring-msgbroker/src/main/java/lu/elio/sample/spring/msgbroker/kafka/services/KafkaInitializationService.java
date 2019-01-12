package lu.elio.sample.spring.msgbroker.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service
public class KafkaInitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaInitializationService.class);

    private KafkaBrokerService kafkaBrokerService;

    @Autowired
    public KafkaInitializationService(KafkaBrokerService kafkaBrokerService) {
        this.kafkaBrokerService = kafkaBrokerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        kafkaBrokerService.registerConsumer("topic1", "group-1", (message) -> {
            LOGGER.info("group-1: Received message for topic1: '{}'", message);
        });
        kafkaBrokerService.registerConsumer("topic2", "group-2", (message) -> {
            LOGGER.info("group-2: Received message for topic2: '{}'", message);
        });
        kafkaBrokerService.registerConsumer("topic3", "group-3", (message) -> {
            LOGGER.info("group-3: Received message for topic3: '{}'", message);
        });

        LOGGER.info("Sending simple message to Kafka topics");
        kafkaBrokerService.sendMessage("topic1", "Hi Welcome to Spring 1.1 For Apache Kafka");
        kafkaBrokerService.sendMessage("topic2", "Hi Welcome to Spring 2.1 For Apache Kafka");
        kafkaBrokerService.sendMessage("topic2", "Hi Welcome to Spring 2.2 For Apache Kafka");
        kafkaBrokerService.sendMessage("topic1", "Hi Welcome to Spring 1.2 For Apache Kafka");
        kafkaBrokerService.sendMessage("topic3", "Hi Welcome to Spring 3.1 For Apache Kafka");

        kafkaBrokerService.registerConsumer("topic3", "group-4", (message) -> {
            LOGGER.info("group-4: Received message for topic3: '{}'", message);
        });
    }

}
