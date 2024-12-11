package com.arthur.calculator.validators;

import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.exceptions.CalculatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PositionValidator implements Validator {
    @Override
    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        log.debug("Validating position");
        switch (scoringDataDto.getEmployment().getPosition()) {
            case MIDDLE_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-2)));
                break;
            case SENIOR_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
                break;
            default:
                throw new CalculatorException("Несуществующая должность");
        }
    }
}

