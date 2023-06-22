package com.sse.publisher.helpers;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;

import java.util.ArrayList;
import java.util.List;

public class DeclarableBuilder {

    private final List<Declarable> declarableList = new ArrayList<>();
    private Queue queue;
    private TopicExchange exchange;
    private final AmqpAdmin amqpAdmin;

    private DeclarableBuilder(final AmqpAdmin amqpAdmin) {
        super();
        this.amqpAdmin = amqpAdmin;
    }

    public static DeclarableBuilder getBuilder(final AmqpAdmin amqpAdmin) {
        return new DeclarableBuilder(amqpAdmin);
    }

    public DeclarableBuilder createQueue(final String queueName) {

        this.queue = QueueBuilder.nonDurable(queueName).autoDelete().build();

        amqpAdmin.declareQueue(this.queue);

        return this;
    }

    public DeclarableBuilder defineExchange(final String exchangeName) {

        this.exchange = new TopicExchange(exchangeName);

        amqpAdmin.declareExchange(this.exchange);

        return this;
    }

    public void addBinding(final String routingKey) {

        amqpAdmin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with(routingKey));
    }
}
