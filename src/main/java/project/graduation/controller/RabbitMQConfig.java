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

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "typers.filter";
    private static final String PLY2PCD_QUEUE_NAME = "typers.filter.ply2pcd";
    private static final String PCDOUTLIER_QUEUE_NAME = "typers.filter.outlier";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue ply2pcdQueue() {
        return new Queue(PLY2PCD_QUEUE_NAME);
    }

    @Bean
    Queue pcdOutlierQueue() {
        return new Queue(PCDOUTLIER_QUEUE_NAME);
    }

    @Bean
    Binding ply2pcdBinding(TopicExchange exchange) {
        return BindingBuilder.bind(ply2pcdQueue()).to(exchange).with(PLY2PCD_QUEUE_NAME);
    }

    @Bean
    Binding pcdOutlierBinding(TopicExchange exchange) {
        return BindingBuilder.bind(pcdOutlierQueue()).to(exchange).with(PCDOUTLIER_QUEUE_NAME);
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

