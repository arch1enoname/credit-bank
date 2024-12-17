package com.neoflex.creditbank.dealservice.services;

import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.entities.Client;
import com.neoflex.creditbank.dealservice.entities.Statement;
import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import com.neoflex.creditbank.dealservice.enums.ChangeType;

import java.util.List;
import java.util.UUID;

public interface StatementService {
    List<Statement> getAllStatements();

    Statement createStatement(Client client);

    Statement getStatementById(UUID statementId);

    void updateStatement(Statement statement, LoanOfferDto request);

    Statement updateStatusAndStatusHistory(Statement statement, ApplicationStatus applicationStatus, ChangeType changeType);
}
