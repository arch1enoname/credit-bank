package com.arthur.calculator.utils.validators;


import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.exceptions.CalculatorException;

public interface Validator {
    void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException;
}
