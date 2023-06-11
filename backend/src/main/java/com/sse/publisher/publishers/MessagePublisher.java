package com.sse.publisher.publishers;

import com.sse.publisher.constants.RabbitConstants;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {


    private final AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(
            final String routingKey,
            final byte[] message,
            final String traceId) {
        try {
            amqpTemplate.send(
                    exchangeName,
                    routingKey,
                    MessageBuilder.withBody(message)
                            .setHeader(RabbitConstants.TRACE_ID_HEADER, traceId)
                            .setContentType(MediaType.APPLICATION_JSON_VALUE)
                            .build());

        } catch (final Exception e) {
            throw GlobalException.getBuilder(LOGGER).setExceptionType(ExceptionType.QUEUE_ERROR);
        }
    }
}
