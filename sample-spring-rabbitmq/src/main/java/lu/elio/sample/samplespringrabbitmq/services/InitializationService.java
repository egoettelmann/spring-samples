package lu.elio.sample.samplespringrabbitmq.services;

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
    private SenderService senderService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        senderService.send("Hello world 1");
        senderService.send("Hello world 2");
        senderService.send("Hello world 3");
    }

}
