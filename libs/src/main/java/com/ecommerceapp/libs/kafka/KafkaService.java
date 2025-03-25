package com.ecommerceapp.libs.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Map<String, String> headers, Object message) {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<String, Object>(topic, message);
        if (headers != null) {
            headers.forEach((k, v) -> {
                producerRecord.headers().add(k, v.getBytes());
            });
        }
        this.kafkaTemplate.send(producerRecord);
    }
}
