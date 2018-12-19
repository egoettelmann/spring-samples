package lu.elio.sample.samplespringrabbitmq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "hello")
public class ReceiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverService.class);

    @RabbitHandler
    public void receive(String in) {
        LOGGER.info("Received: {}", in);
    }

}
