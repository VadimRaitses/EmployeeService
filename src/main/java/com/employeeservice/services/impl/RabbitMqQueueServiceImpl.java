package com.employeeservice.services.impl;

import com.employeeservice.services.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author Raitses Vadim
 */

@Service
public class RabbitMqQueueServiceImpl implements QueueService<String> {


    private final RabbitTemplate rabbitTemplate;
    private final String exchangeTopic;
    private Logger logger = LoggerFactory.getLogger(RabbitMqQueueServiceImpl.class);

    @Autowired
    public RabbitMqQueueServiceImpl(@NotNull RabbitTemplate rabbitTemplate,
                                    @NotNull @Qualifier("exchangeTopic") String exchangeTopic) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeTopic = exchangeTopic;

    }

    @Override
    public void sendMessage(String eventName, String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeTopic, message);
        } catch (AmqpException e) {
            logger.error(e.getLocalizedMessage());
        }
    }
}
