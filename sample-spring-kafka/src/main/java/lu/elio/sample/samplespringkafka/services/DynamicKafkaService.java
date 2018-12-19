package lu.elio.sample.samplespringkafka.services;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class DynamicKafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicKafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String value) {
        LOGGER.info("Sending message on topic '{}", topic);
        kafkaTemplate.send(topic, value);
    }

    public void registerConsumer(String topic, String groupId, Consumer<String> callback) {
        LOGGER.info("Registering consumer for topic '{}' and group '{}'", topic, groupId);
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(groupId);
        containerProps.setMessageListener(new MessageListener<String, String>() {

            @Override
            public void onMessage(ConsumerRecord<String, String> message) {
                LOGGER.info("Received message in group '{}' on topic '{}'", groupId, topic);
                callback.accept(message.value());
            }

        });
        ConcurrentMessageListenerContainer<String, String> container = createContainer(groupId, containerProps);
        //container.setBeanName("testAuto");
        container.start();
    }

    private ConcurrentMessageListenerContainer<String, String> createContainer(String groupId, ContainerProperties containerProps) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(props);
        return new ConcurrentMessageListenerContainer<>(cf, containerProps);
    }

}
