package project.graduation.controller;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "typers.filter";
    public static final String QUEUE_NAME = "typers-filter-queue";
    public static final String KEY_PLY2PCD = "ply2pcd";
    public static final String KEY_INLIER = "inlier";
    public static final String KEY_COMPLETE = "complete";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    List<Binding> bindings(Queue queue, TopicExchange exchange) {
        return Arrays.asList(
                BindingBuilder.bind(queue).to(exchange).with(KEY_PLY2PCD),
                BindingBuilder.bind(queue).to(exchange).with(KEY_INLIER),
                BindingBuilder.bind(queue).to(exchange).with(KEY_COMPLETE)
        );
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}

