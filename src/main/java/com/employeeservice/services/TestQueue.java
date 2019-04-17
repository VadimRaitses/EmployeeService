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
        admin.declareQueue(new Queue("myqueue"));


        AmqpTemplate template = new RabbitTemplate(connectionFactory);

        for (int i = 0; i < 100; i++) {
            template.convertAndSend("myqueue", Integer.toString(i));
        }
        for (int j = 0; j < 50; j++) {

            Object foo = template.receiveAndConvert("myqueue");
            System.out.println(foo);

        }
        Thread.sleep(1000);
        System.out.println("sleep");
        while(true){

            Object foo = template.receiveAndConvert("myqueue");
            System.out.println(foo);

        }

    }
}
