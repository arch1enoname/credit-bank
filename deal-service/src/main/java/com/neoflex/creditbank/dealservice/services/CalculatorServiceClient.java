package com.neoflex.creditbank.dealservice.services;

import com.neoflex.creditbank.dealservice.dtos.CreditDto;
import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import com.neoflex.creditbank.dealservice.dtos.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class CalculatorServiceClient {
    private final RestClient restClient;

    public CalculatorServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080/api/calculator").build();
        log.info("CalculatorServiceClient initialized with base URL: http://localhost:8080/api/calculator");
    }

    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Sending request to /offers with body: {}", loanStatementRequestDto);

        ResponseEntity<List<LoanOfferDto>> response = restClient.post()
                .uri("/offers")
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});

        log.info("Received response from /offers: {}", response);
        return response;
    }

    public ResponseEntity<CreditDto> calculate(ScoringDataDto scoringDataDto) {
        log.info("Sending request to /calc with body: {}", scoringDataDto);

        ResponseEntity<CreditDto> response = restClient.post()
                .uri("/calc")
                .body(scoringDataDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});

        log.info("Received response from /calc: {}", response);
        return response;
    }
}
