package com.arthur.calculator.utils.validators;

import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.exceptions.CalculatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Slf4j
@Component
public class EmploymentStatusValidator implements Validator {
    @Override
    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        log.debug("Validating employment status");
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new CalculatorException("Безработный");
            case SELF_EMPLOYED:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(2)));
                break;
            case BUSINESS_OWNER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(1)));
                break;
            case EMPLOYED:
                break;
            default:
                throw new CalculatorException("Несуществующий статус");
        }
    }
}
