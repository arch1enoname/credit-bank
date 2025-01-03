package com.arthur.statementservice.services;

import com.arthur.statementservice.dto.LoanOfferDto;
import com.arthur.statementservice.dto.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {

    private DealRestServiceClient restServiceClient;

    @Autowired
    public StatementService(DealRestServiceClient restServiceClient) {
        this.restServiceClient = restServiceClient;
    }

    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return restServiceClient.getLoanOffers(loanStatementRequestDto).getBody();
    }

    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        restServiceClient.selectOffer(loanOfferDto);
    }
}
