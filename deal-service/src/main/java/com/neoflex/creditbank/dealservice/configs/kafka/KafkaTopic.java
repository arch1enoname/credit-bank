package com.neoflex.creditbank.dealservice.configs.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name("finish-registration").build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name("send-documents").build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name("credit-issued").build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name("statement-denied").build();
    }

    @Bean
    public NewTopic createDocumentTopic() {
        return TopicBuilder.name("create-document").build();
    }

    @Bean
    public NewTopic sendSes() {
        return TopicBuilder.name("send-ses").build();
    }
}
