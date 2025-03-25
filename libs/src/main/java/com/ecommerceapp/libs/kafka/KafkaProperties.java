package com.ecommerceapp.libs.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "spring.kafka")
@ConfigurationPropertiesScan
@Data
public class KafkaProperties {
    private String bootstrapServers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsumerConfig {
        private String consumerGroupId;

    }

    private ConsumerConfig consumer;

}
