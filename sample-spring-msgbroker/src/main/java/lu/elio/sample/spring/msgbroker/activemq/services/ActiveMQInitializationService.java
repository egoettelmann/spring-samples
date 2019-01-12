package lu.elio.sample.spring.msgbroker.activemq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Profile("activemq")
@Service
public class ActiveMQInitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQInitializationService.class);

    private ActiveMQBrokerService activeMQBrokerService;

    @Autowired
    public ActiveMQInitializationService(ActiveMQBrokerService activeMQBrokerService) {
        this.activeMQBrokerService = activeMQBrokerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        activeMQBrokerService.registerConsumer("queue1", (message) -> {
            LOGGER.info("Received message for queue1: '{}'", message);
        });
        activeMQBrokerService.registerConsumer("queue2", (message) -> {
            LOGGER.info("Received message for queue2: '{}'", message);
        });
        activeMQBrokerService.registerConsumer("queue3", (message) -> {
            LOGGER.info("Received message for queue3: '{}'", message);
        });

        LOGGER.info("Sending simple message to ActiveMQ queues");
        activeMQBrokerService.sendMessage("queue1", "Hi Welcome to Spring 1.1 For ActiveMQ");
        activeMQBrokerService.sendMessage("queue2", "Hi Welcome to Spring 2.1 For ActiveMQ");
        activeMQBrokerService.sendMessage("queue2", "Hi Welcome to Spring 2.2 For ActiveMQ");
        activeMQBrokerService.sendMessage("queue1", "Hi Welcome to Spring 1.2 For ActiveMQ");
        activeMQBrokerService.sendMessage("queue3", "Hi Welcome to Spring 3.1 For ActiveMQ");

        activeMQBrokerService.registerConsumer("queue3", (message) -> {
            LOGGER.info("Received message for queue3: '{}'", message);
        });
    }

}
