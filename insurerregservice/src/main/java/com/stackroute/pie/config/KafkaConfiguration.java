package com.stackroute.pie.config;

import com.stackroute.pie.domain.Insurer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    //Giving the kafka configuration
    @Bean
    public ProducerFactory<String, Insurer> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        System.out.println("inside producer factory*************************");
        return new DefaultKafkaProducerFactory<>(config);
    }
    //Giving the Kafkatemplate to be produced
    @Bean
    public KafkaTemplate<String, Insurer> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
