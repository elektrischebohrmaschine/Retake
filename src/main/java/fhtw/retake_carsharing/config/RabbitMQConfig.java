package fhtw.retake_carsharing.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CAR_STATUS_QUEUE = "car-status-queue";
    public static final String EMERGENCY_QUEUE = "emergency-queue";
    public static final String GEN_REPORT_QUEUE = "gen-report-queue";

    @Bean
    public Queue carStatusQueue() {
        return QueueBuilder.durable(CAR_STATUS_QUEUE).build();
    }

    @Bean
    public Queue emergencyQueue() {
        return QueueBuilder.durable(EMERGENCY_QUEUE).build();
    }

    @Bean
    public Queue genReportQueue() {
        return QueueBuilder.durable(GEN_REPORT_QUEUE).build();
    }

    @Bean
    public ConnectionFactory connectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password) {
        CachingConnectionFactory factory = new CachingConnectionFactory(host, port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}