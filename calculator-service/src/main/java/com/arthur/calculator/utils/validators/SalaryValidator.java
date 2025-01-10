package com.arthur.calculator.utils.validators;

import com.arthur.calculator.exceptions.CalculatorException;
import com.arthur.calculator.dtos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaryValidator implements Validator {
    @Override
    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        log.debug("Validating salary");
        if (scoringDataDto.getAmount().doubleValue() > scoringDataDto.getTerm().doubleValue() * scoringDataDto.getEmployment().getSalary().doubleValue()) {
            throw new CalculatorException("Сумма займа больше, чем 24 зарплат");
        }
    }
}

