package com.arthur.calculator.controllers;

import com.arthur.calculator.dtos.*;
import com.arthur.calculator.services.impl.CalculatorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Calculator Controller", description = "Контроллер для расчета кредита")
public class CalculatorController {


    private final CalculatorServiceImpl calculatorService;

    @Autowired
    public CalculatorController(CalculatorServiceImpl calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Operation(summary = "Getting Offers", description = "Предоставление кредитных предложений")
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@Validated
                                                            @RequestBody
                                                            @Parameter(description = "Данные для создания предложений", required = true)
                                                        LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Received credit request: {}", loanStatementRequestDto);
        return ResponseEntity.ok(calculatorService.getOffers(loanStatementRequestDto));
    }


    @Operation(summary = "Calculate Credit", description = "Полный расчет параметров кредита")
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculate(@Validated
                                                   @RequestBody
                                                   @Parameter(description = "Данные для оценки платежеспособности клиента", required = true)
                                               ScoringDataDto scoringDataDto){
        log.info("Received loan offer request: {}", scoringDataDto);
        return ResponseEntity.ok(calculatorService.calculate(scoringDataDto));
    }
}
