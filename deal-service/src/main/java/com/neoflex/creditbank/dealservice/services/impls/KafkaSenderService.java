package com.neoflex.creditbank.dealservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.EmailMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSenderService {

    @Autowired
    private KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    public void sendMessage(String topicName, EmailMessageDto emailMessageDto) {
        log.info("Sending : {}", emailMessageDto);
        kafkaTemplate.send(topicName, emailMessageDto);
    }
}
