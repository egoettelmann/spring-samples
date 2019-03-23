Sample-Spring-MsgBroker
=======================

This application gives different sample implementations for message brokers.

## Kafka

Before starting the app, Zookeeper and Kafka need to be started:

```
zkServer start
kafka-server-start /usr/local/etc/kafka/server.properties
```

Then, simply start the main application with the `kafka` Spring profile.

## RabbitMQ

Before starting the app, RabbitMQ needs to be started:

```
rabbitmq-server start
```

Then, simply start the main application with the `rabbitmq` Spring profile.

Stop it with:
```
rabbitmqctl stop
```


## ActiveMQ

Before starting the app, ActiveMQ needs to be started:

```
activemq start
```

Then, simply start the main application with the 'activemq' Spring profile.
