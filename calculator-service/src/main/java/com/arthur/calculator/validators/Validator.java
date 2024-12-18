package com.arthur.calculator.validators;


import com.arthur.calculator.exceptions.CalculatorException;
import com.arthur.calculator.dtos.*;

public interface Validator {
    void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException;
}
