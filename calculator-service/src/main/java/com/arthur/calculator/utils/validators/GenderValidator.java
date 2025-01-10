package com.arthur.calculator.utils.validators;

import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Component
public class GenderValidator implements Validator {
    @Override
    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) {
        log.debug("Validating gender");
        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();
        if (age > 32 && age < 60 && scoringDataDto.getGender().equals(Gender.FEMALE)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
        } else if (age < 55 && age > 30 && scoringDataDto.getGender().equals(Gender.MALE)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
        } else if (scoringDataDto.getGender().equals(Gender.NON_BINARY)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(7)));
        }
    }
}

