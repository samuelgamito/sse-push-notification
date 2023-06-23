package com.sse.publisher.configs;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableRabbit
class RabbitConfig {


    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    private static final boolean DURABLE = false;
    private static final boolean TRANSIENT = true;
    private static final boolean AUTO_DELETED = false;
    private static final boolean MANUALLY_DELETED = false;
    private static final String ALTERNATE_EXCHANGE_NAME = "_alternate";
    private static final Map<String, Object> EMPTY_ARGUMENTS = Map.of();

    @Bean
    public ConnectionFactory connectionFactoryUser(final RabbitProperties properties) {
        final CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(properties.getHost(), properties.getPort());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        connectionFactory.setVirtualHost(properties.getVirtualHost());
        return connectionFactory;
    }

    @Bean
    public TopicExchange declareExchanges() {
        System.out.println(exchangeName);
        return new TopicExchange(exchangeName);
    }
}