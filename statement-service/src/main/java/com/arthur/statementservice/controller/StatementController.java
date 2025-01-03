package com.arthur.statementservice.controller;

import com.arthur.statementservice.dto.LoanOfferDto;
import com.arthur.statementservice.dto.LoanStatementRequestDto;
import com.arthur.statementservice.services.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statement")
public class StatementController {

    private final StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity.ok(statementService.getLoanOffers(loanStatementRequestDto));
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        statementService.selectLoanOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }
}
