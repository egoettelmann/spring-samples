package lu.elio.sample.springkafkasample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleKafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        LOGGER.info("Sending simple message to Kafka topic");
        kafkaTemplate.send("tutorialspoint", "Hi Welcome to Spring For Apache Kafka");
    }

}
