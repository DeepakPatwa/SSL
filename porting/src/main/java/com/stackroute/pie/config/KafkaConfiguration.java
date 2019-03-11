////package com.stackroute.pie.config;
//
//package com.stackroute.pie.config;
//
//
////import com.stackroute.pie.domain.CommonAuth;
////import com.stackroute.pie.domain.Insured;
//import com.stackroute.pie.domain.OptionalFields;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//@EnableKafka
//@Configuration
//public class KafkaConfiguration {
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        System.out.println("Inside consumer factory------------");
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//    @Bean
//    public ConsumerFactory<String, OptionalFields> userConsumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        System.out.println("Inside consumer factory json------------");
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
//                new JsonDeserializer<>(OptionalFields.class));
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, OptionalFields> userKafkaListenerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, OptionalFields> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(userConsumerFactory());
//        return factory;
//    }
//
//}
