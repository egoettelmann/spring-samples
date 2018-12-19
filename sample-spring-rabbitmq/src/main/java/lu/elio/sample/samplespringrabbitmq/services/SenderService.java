package lu.elio.sample.samplespringrabbitmq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenderService.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send(String message) {
        template.convertAndSend(queue.getName(), message);
        LOGGER.info("Sending message");
    }

}
