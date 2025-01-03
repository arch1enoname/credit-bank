package com.arthur.statementservice.services;

import com.arthur.statementservice.dto.LoanOfferDto;
import com.arthur.statementservice.dto.LoanStatementRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class DealRestServiceClient {
    private final RestClient restClient;

    public DealRestServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8081/api/deal").build();
        log.info("DealServiceClient initialized with base URL: http://localhost:8081/api/deal");
    }

    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Sending request to /statement with body: {}", loanStatementRequestDto);

        ResponseEntity<List<LoanOfferDto>> response = restClient.post()
                .uri("/statement")
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        log.info("Received response from /statement: {}", response);
        return response;
    }

    public ResponseEntity<Void> selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Sending request to /offer/select with body: {}", loanOfferDto);

        ResponseEntity<Void> response = restClient.post()
                .uri("/offer/select")
                .body(loanOfferDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        log.info("Received response from /offer/select: {}", response);
        return response;
    }
}
