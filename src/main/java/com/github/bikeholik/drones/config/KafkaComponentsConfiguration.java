package com.github.bikeholik.drones.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import static com.github.bikeholik.drones.common.Constants.TOPIC_TRAFFIC_REPORTS;

@Configuration
class KafkaComponentsConfiguration {

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

    @Bean
    NewTopic trafficReportsTopic() {
        return new NewTopic(TOPIC_TRAFFIC_REPORTS, 1, (short) 1);
    }

    @Bean
    BeanPostProcessor kafkaAdminBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof KafkaAdmin) {
                    ((KafkaAdmin) bean).setOperationTimeout(120);
                }
                return bean;
            }
        };
    }
}
