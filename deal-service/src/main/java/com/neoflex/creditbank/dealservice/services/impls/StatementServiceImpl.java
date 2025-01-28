package com.neoflex.creditbank.dealservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.StatusHistoryDto;
import com.neoflex.creditbank.dealservice.entities.Client;
import com.neoflex.creditbank.dealservice.entities.Statement;
import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import com.neoflex.creditbank.dealservice.enums.ChangeType;
import com.neoflex.creditbank.dealservice.exceptions.StatementNotFoundException;
import com.neoflex.creditbank.dealservice.repositories.StatementRepository;
import com.neoflex.creditbank.dealservice.services.StatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    public List<Statement> getAllStatements() {
        log.info("Fetching all statements.");
        return statementRepository.findAll();
    }

    @Override
    public Statement createStatement(Client client) {
        log.info("Creating statement for client: {}", client);

        Statement statement = Statement.builder()
                .client(client)
                .creationDate(LocalDateTime.now())
                .applicationStatus(ApplicationStatus.PREAPPROVAL)
                .statusHistory(Collections.singletonList(createStatusHistoryJsonb(ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC)))
                .build();

        Statement savedStatement = statementRepository.save(statement);

        log.info("Successfully created and saved statement: {}", savedStatement);
        return savedStatement;
    }

    @Override
    public Statement getStatementById(UUID statementId) {
        log.info("Fetching statement by ID: {}", statementId);

        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException("There is no statement with id: " + statementId));

        log.info("Found statement: {}", statement);
        return statement;
    }

    @Override
    public void updateStatement(Statement statement, LoanOfferDto loanOfferDto) {
        log.info("Updating statement with loan offer: {}", loanOfferDto);

        statement.setAppliedOffer(loanOfferDto);
        updateStatusAndStatusHistory(statement, ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);

        Statement updatedStatement = statementRepository.save(statement);

        log.info("Successfully updated statement: {}", updatedStatement);
    }

    @Override
    public Statement updateStatusAndStatusHistory(Statement statement, ApplicationStatus applicationStatus, ChangeType changeType) {
        log.info("Updating status and history of statement: {}, new status: {}, change type: {}", statement, applicationStatus, changeType);

        StatusHistoryDto statusHistoryJsonb = createStatusHistoryJsonb(applicationStatus, changeType);
        addStatusHistory(statement, statusHistoryJsonb);
        statement.setApplicationStatus(statusHistoryJsonb.getStatus());

        Statement updatedStatement = statementRepository.save(statement);

        log.info("Successfully updated status and history for statement: {}", updatedStatement);
        return updatedStatement;
    }

    private void addStatusHistory(Statement statement, StatusHistoryDto statusHistoryJsonb) {
        log.info("Adding status history entry to statement. Current history: {}", statement.getStatusHistory());

        List<StatusHistoryDto> list = statement.getStatusHistory();
        list.add(statusHistoryJsonb);
        statement.setStatusHistory(list);

        log.info("Status history updated. New history: {}", list);
    }

    private StatusHistoryDto createStatusHistoryJsonb(ApplicationStatus applicationStatus, ChangeType changeType) {
        log.info("Creating new status history entry: status = {}, changeType = {}", applicationStatus, changeType);

        StatusHistoryDto statusHistoryDto = StatusHistoryDto.builder()
                .status(applicationStatus)
                .changeType(changeType)
                .time(LocalDateTime.now())
                .build();

        log.debug("Created status history entry: {}", statusHistoryDto);
        return statusHistoryDto;
    }
}
