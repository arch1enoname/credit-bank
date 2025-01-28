package com.arthur.gatewayservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@Slf4j
public class StatementRestService {
    private final RestClient restClient;

    public StatementRestService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082/api/statement").build();
        log.info("DealServiceClient initialized with base URL: http://localhost:8082/api/statement");
    }

    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {

        ResponseEntity<List<LoanOfferDto>> response = restClient.post()
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        return response;
    }

    public ResponseEntity<Void> selectOffer(LoanOfferDto loanOfferDto) {

        ResponseEntity<Void> response = restClient.post()
                .uri("/offer")
                .body(loanOfferDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        return response;
    }
}
