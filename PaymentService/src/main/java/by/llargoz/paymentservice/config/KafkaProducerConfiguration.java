package by.llargoz.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder
                .name(topicName)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

}
