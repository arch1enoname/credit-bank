package com.neoflex.creditbank.dealservice.services;

import com.neoflex.creditbank.dealservice.dtos.FinishRegistrationRequestDto;
import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request);

    void selectLoanOffer(LoanOfferDto loanOfferDto);

    void calculateLoan(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);
}
