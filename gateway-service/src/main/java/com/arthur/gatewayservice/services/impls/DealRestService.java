package com.arthur.gatewayservice.services.impls;

import com.neoflex.creditbank.dealservice.entities.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@Slf4j
public class DealRestService {
    private final RestClient restClient;

    public DealRestService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8081/api/deal").build();
        log.info("DealServiceClient initialized with base URL: http://localhost:8081/api/deal");
    }

    public ResponseEntity<Void> calculate(String statementId) {

        ResponseEntity<Void> response = restClient.post()
                .uri("/api/deal/calculate/{statementId}", statementId)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        return response;
    }

    public ResponseEntity<List<Statement>> getAllStatement() {
        ResponseEntity<List<Statement>> response = restClient.get()
                .uri("/api/deal/admin/statement")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Statement>>() {
                });

        return response;
    }

    public ResponseEntity<Statement> getStatementById(String statementId) {
        ResponseEntity<Statement> response = restClient.get()
                .uri("/api/deal/admin/statement/"+statementId)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Statement>() {
                });

        return response;
    }



}
