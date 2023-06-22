package com.sse.publisher.configs.factors;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerContainerFactory {

    private final ConnectionFactory connectionFactory;

    public MessageListenerContainerFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public MessageListenerContainer createMessageListenerContainer(String queueName) {
        SimpleMessageListenerContainer mlc = new SimpleMessageListenerContainer(connectionFactory);
        mlc.addQueueNames(queueName);
        return mlc;
    }
}