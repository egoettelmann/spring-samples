package lu.elio.sample.spring.msgbroker.core;

import java.util.function.Consumer;

public interface IMsgBrokerService {

    void sendMessage(String topic, String value);

    void registerConsumer(String topic, String groupId, Consumer<String> callback);

}
