package lu.elio.sample.spring.msgbroker;

import lu.elio.sample.spring.msgbroker.core.IMsgBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MsBrokerInitializationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsBrokerInitializationService.class);

    private IMsgBrokerService msgBrokerService;

    @Autowired
    public MsBrokerInitializationService(IMsgBrokerService msgBrokerService) {
        this.msgBrokerService = msgBrokerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        msgBrokerService.registerConsumer("exchange1", "route-1", (message) -> {
            LOGGER.info("route-1: Received message for exchange1: '{}'", message);
        });
        msgBrokerService.registerConsumer("exchange2", "route-2", (message) -> {
            LOGGER.info("route-2: Received message for exchange2: '{}'", message);
        });
        msgBrokerService.registerConsumer("exchange3", "route-3", (message) -> {
            LOGGER.info("route-3: Received message for exchange3: '{}'", message);
        });

        LOGGER.info("Sending simple message to RabbitMQ exchanges");
        msgBrokerService.sendMessage("exchange1", "Hi Welcome to Spring 1.1");
        msgBrokerService.sendMessage("exchange2", "Hi Welcome to Spring 2.1");
        msgBrokerService.sendMessage("exchange2", "Hi Welcome to Spring 2.2");
        msgBrokerService.sendMessage("exchange1", "Hi Welcome to Spring 1.2");
        msgBrokerService.sendMessage("exchange3", "Hi Welcome to Spring 3.1");

        msgBrokerService.registerConsumer("exchange3", "route-4", (message) -> {
            LOGGER.info("route-4: Received message for exchange3: '{}'", message);
        });
    }

}
