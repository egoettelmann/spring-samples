package lu.elio.sample.spring.msgbroker.activemq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.function.Consumer;

@Profile("activemq")
@Service
public class ActiveMQBrokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQBrokerService.class);

    private JmsTemplate jmsTemplate;

    private DefaultJmsListenerContainerFactory containerFactory;

    @Autowired
    public ActiveMQBrokerService(JmsTemplate jmsTemplate,
                                 DefaultJmsListenerContainerFactory containerFactory
    ) {
        this.jmsTemplate = jmsTemplate;
        this.containerFactory = containerFactory;
    }

    public void sendMessage(String topic, String value) {
        LOGGER.info("Sending message on topic '{}'", topic);
        jmsTemplate.convertAndSend(topic, value);
    }

    public void registerConsumer(String queueName, Consumer<String> callback) {
        MessageListener messageListener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                LOGGER.info("Received message in queueName '{}'", queueName);
                try {
                    callback.accept(message.getBody(String.class));
                } catch (JMSException e) {
                    LOGGER.error("Impossible to read message body");
                }
            }
        };
        createContainer(queueName, messageListener);
    }

    public void createContainer(String queueName, MessageListener listener) {
        SimpleJmsListenerEndpoint listenerEndpoint = new SimpleJmsListenerEndpoint();
        listenerEndpoint.setMessageListener(listener);
        listenerEndpoint.setDestination(queueName);
        DefaultMessageListenerContainer container = containerFactory.createListenerContainer(listenerEndpoint);
        container.setMessageListener(listener);
        container.start();
    }

}
