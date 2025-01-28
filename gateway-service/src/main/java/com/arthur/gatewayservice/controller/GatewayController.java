package com.arthur.gatewayservice.controller;

import com.arthur.gatewayservice.services.impls.DealRestService;
import com.arthur.gatewayservice.services.impls.StatementRestService;
import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import com.neoflex.creditbank.dealservice.entities.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/gateway")
public class GatewayController {
    private final StatementRestService statementRestService;
    private final DealRestService dealRestService;

    @Autowired
    public GatewayController(StatementRestService statementRestService, DealRestService dealRestService) {
        this.statementRestService = statementRestService;
        this.dealRestService = dealRestService;
    }

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto ) {
        return ResponseEntity.ok(statementRestService.getLoanOffers(loanStatementRequestDto).getBody());
    }

    @PostMapping("/statement/offer")
    public ResponseEntity<Void> selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        return ResponseEntity.ok(statementRestService.selectOffer(loanOfferDto).getBody());
    }

    @PostMapping("/deal/calculate/{statementId}")
    public ResponseEntity<Void> calculate(@PathVariable("statementId") String statementId) {
        return ResponseEntity.ok(dealRestService.calculate(statementId).getBody());
    }

    @GetMapping("/api/deal/admin/statement")
    public ResponseEntity<List<Statement>> getAllStatement() {
        return ResponseEntity.ok(dealRestService.getAllStatement().getBody());
    }

    @GetMapping("/api/deal/admin/statement/{statementId}")
    public ResponseEntity<Statement> getStatementById(@PathVariable String statementId) {
        return ResponseEntity.ok(dealRestService.getStatementById(statementId).getBody());
    }


}
