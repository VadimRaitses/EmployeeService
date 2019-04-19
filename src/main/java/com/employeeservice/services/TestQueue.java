package com.employeeservice.services;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TestQueue {


    public static void main(String[] args) throws InterruptedException {


        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("check"));


        AmqpTemplate template = new RabbitTemplate(connectionFactory);

        for (int i = 0; i < 100; i++) {
            template.convertAndSend("check", Integer.toString(i));
        }
        for (int j = 0; j < 50; j++) {
            Thread.sleep(1000);
            Object foo = template.receiveAndConvert("check");
            System.out.println(foo);

        }

    }
}
