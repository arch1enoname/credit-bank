package com.arthur.calculator.utils;


import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.validators.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class ScoringDataValidator {
    private final List<Validator> validators;

    @Autowired
    public ScoringDataValidator(List<Validator> validators) {
        this.validators = validators;
    }

    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) {
        for (Validator validator:validators) {
            validator.validate(scoringDataDto, creditDto);
        }
    }

}
