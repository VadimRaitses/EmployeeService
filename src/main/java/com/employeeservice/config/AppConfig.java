package com.employeeservice.config;

import com.employeeservice.dao.MongoDaoRepository;
import com.employeeservice.services.impl.DepartmentServiceImpl;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Raitses Vadim
 */


@Configuration
public class AppConfig {


    @Bean
    MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean
    RabbitMqProperties rabbitMqProperties() {
        return new RabbitMqProperties();
    }

    @Lazy
    @Bean
    public MongoClient mongo() {
        return new MongoClient(mongoProperties().getHost(), mongoProperties().getPort());
    }

    @Lazy
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), mongoProperties().getDatabase());
    }

    @Lazy
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(rabbitMqProperties().getHost());
    }

    @Lazy
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    Gson getGson() {
        return new Gson();
    }

    @Bean
    Queue queue() {
        return new Queue(rabbitMqProperties().getQueueName());
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitMqProperties().getTopicExchangeName());
    }


//    @Bean
//    public DepartmentServiceImpl departmentService() {
//        return new DepartmentServiceImpl(mongoRepositoryDao());
//    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean("exchangeTopic")
    public String exchangeTopic() {
        return rabbitMqProperties().getTopicExchangeName();
    }


    @Bean("mongoRepository")
    public MongoDaoRepository mongoRepositoryDao() {
        return new MongoDaoRepository(mongoTemplate());

    }


}
