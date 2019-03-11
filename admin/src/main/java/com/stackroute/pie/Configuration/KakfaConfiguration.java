//package com.stackroute.pie.Configuration;
//
//import com.stackroute.pie.domain.FormFormat;
////TODO: Remove the Insured imported class from below
////import com.stackroute.pie.domain.Insured;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KakfaConfiguration {
//    @Bean
//    public ProducerFactory<String, FormFormat> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        System.out.println("inside producer factory*************************");
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//    @Bean
//    public KafkaTemplate<String, FormFormat> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}