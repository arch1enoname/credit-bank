package com.arthur.calculator.services;

import com.arthur.calculator.dtos.*;
import com.arthur.calculator.exceptions.CalculatorException;

import java.util.List;

public interface CalculatorService {
    CreditDto calculate(ScoringDataDto scoringDataDto) throws CalculatorException;
    List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequestDto) throws CalculatorException;
}
