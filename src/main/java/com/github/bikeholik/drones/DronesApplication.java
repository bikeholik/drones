package com.github.bikeholik.drones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class DronesApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DronesApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }

    @Bean
    JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    ProducerFactory<?, ?> defaultKafkaProducerFactory(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
        DefaultKafkaProducerFactory<Object, Object> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory, ObjectMapper objectMapper) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        return factory;
    }
}
