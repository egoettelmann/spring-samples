package lu.elio.sample.spring.msgbroker.rabbitmq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Profile("rabbitmq")
@Service
public class RabbitMQBrokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQBrokerService.class);

    private RabbitTemplate rabbitTemplate;

    private RabbitAdmin rabbitAdmin;

    private ConnectionFactory connectionFactory;

    @Autowired
    public RabbitMQBrokerService(RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin, ConnectionFactory connectionFactory) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
        this.connectionFactory = connectionFactory;
    }

    public void sendMessage(String exchange, String value) {
        LOGGER.info("Sending message on exchange '{}'", exchange);
        declareQueueIfNeeded(exchange);
        rabbitTemplate.convertAndSend(exchange, exchange, value);
    }

    public void registerConsumer(String queueName, String routingKey, Consumer<String> callback) {
        MessageListener messageListener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                LOGGER.info("Received message in queueName '{}' on routingKey '{}'", queueName, routingKey);
                callback.accept(new String(message.getBody()));
            }
        };
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(messageListener, new SimpleMessageConverter());
        SimpleMessageListenerContainer container = createContainer(queueName, listenerAdapter);
        container.start();
    }

    public SimpleMessageListenerContainer createContainer(String queueName, MessageListener listener) {
        declareQueueIfNeeded(queueName);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listener);
        return container;
    }

    private void declareQueueIfNeeded(String queueName) {
        Exchange exchange = new TopicExchange(queueName);
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareExchange(exchange);
        if (rabbitAdmin.getQueueProperties(queueName) == null) {
            rabbitAdmin.declareQueue(queue);
        }
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with(queueName).noargs()
        );
    }

}
