package com.arthur.calculator.configs;

import com.arthur.calculator.utils.ScoringDataValidator;
import com.arthur.calculator.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidatorConfig {
    @Autowired
    private List<Validator> validators;

    @Bean
    public ScoringDataValidator scoringDataValidator() {
        return new ScoringDataValidator();
    }
}
