package com.demo.kafkademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    /**
     * Boot will autowire this into the container factory.
     */
    @Bean
    public SeekToCurrentErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
        return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer);
    }

    /**
     * Configure the {@link DeadLetterPublishingRecoverer} to publish poison pill bytes to a dead letter topic:
     * "stock-quotes-avro.DLT".
     */
    @Bean
    public DeadLetterPublishingRecoverer publisher(KafkaTemplate bytesTemplate) {
        return new DeadLetterPublishingRecoverer(bytesTemplate);
    }
}