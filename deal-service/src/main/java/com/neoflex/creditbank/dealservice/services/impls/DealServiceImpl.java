package com.neoflex.creditbank.dealservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.*;
import com.neoflex.creditbank.dealservice.entities.Client;
import com.neoflex.creditbank.dealservice.entities.Statement;
import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import com.neoflex.creditbank.dealservice.enums.ChangeType;
import com.neoflex.creditbank.dealservice.enums.EmailTheme;
import com.neoflex.creditbank.dealservice.services.CalculatorServiceClient;
import com.neoflex.creditbank.dealservice.services.DealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DealServiceImpl implements DealService {

    private final StatementServiceImpl statementService;
    private final ClientServiceImpl clientService;
    private final CalculatorServiceClient calculatorServiceClient;
    private final CreditServiceImpl creditService;
    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    @Autowired
    public DealServiceImpl(StatementServiceImpl statementService, ClientServiceImpl clientService, CalculatorServiceClient calculatorServiceClient, CreditServiceImpl creditService, KafkaTemplate<String, EmailMessageDto> kafkaTemplate) {
        this.statementService = statementService;
        this.clientService = clientService;
        this.calculatorServiceClient = calculatorServiceClient;
        this.creditService = creditService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start processing getLoanOffers with LoanStatementRequestDto: {}", loanStatementRequestDto);

        Statement statement = statementService.createStatement(clientService.createClient(loanStatementRequestDto));
        log.debug("Created statement: {}", statement);

        List<LoanOfferDto> loanOfferDtos = calculatorServiceClient.getLoanOffers(loanStatementRequestDto).getBody();
        if (loanOfferDtos != null) {
            for (LoanOfferDto loanOfferDto : loanOfferDtos) {
                loanOfferDto.setStatementId(statement.getStatementId());
            }
        } else {
            log.warn("LoanOfferDtos is null for LoanStatementRequestDto: {}", loanStatementRequestDto);
        }

        log.info("Successfully processed getLoanOffers. Loan offers: {}", loanOfferDtos);
        return loanOfferDtos;
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        log.info("Start processing selectLoanOffer with LoanOfferDto: {}", loanOfferDto);

        Statement statement = statementService.getStatementById(loanOfferDto.getStatementId());
        log.debug("Retrieved statement for LoanOfferDto: {}", statement);

        statementService.updateStatement(statement, loanOfferDto);

        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .text("Вы выбрали оффер")
                .theme(EmailTheme.FINISH_REGISTRATION)
                .address("ratmuh2809@gmail.com")
                .statementId(statement.getStatementId())
                .build();

        kafkaTemplate.send("finish-registration", emailMessageDto);

        log.info("Successfully updated statement with selected LoanOfferDto: {}", loanOfferDto);
    }

    @Override
    public void calculateLoan(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        log.info("Start processing calculateLoan with FinishRegistrationRequestDto: {} and statementId: {}", finishRegistrationRequestDto, statementId);

        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        log.debug("Retrieved statement: {}", statement);

        Client client = statement.getClient();
        log.debug("Retrieved client from statement: {}", client);

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .dependentAmount(finishRegistrationRequestDto.getDependentAmount())
                .gender(finishRegistrationRequestDto.getGender())
                .maritalStatus(finishRegistrationRequestDto.getMaritalStatus())
                .passportIssueDate(finishRegistrationRequestDto.getPassportIssueDate())
                .passportIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch())
                .employment(finishRegistrationRequestDto.getEmployment())
                .accountNumber(finishRegistrationRequestDto.getAccountNumber())
                .term(statement.getAppliedOffer().getTerm())
                .amount(statement.getAppliedOffer().getRequestedAmount())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .build();

        log.debug("Created ScoringDataDto: {}", scoringDataDto);

        fillScoringDataDtoByStatementClient(scoringDataDto, client);
        log.debug("Filled ScoringDataDto with client data: {}", scoringDataDto);

        CreditDto creditDto = calculatorServiceClient.calculate(scoringDataDto).getBody();
        if (creditDto != null) {
            log.info("Successfully calculated CreditDto: {}", creditDto);
            creditService.createCredit(creditDto);
            statementService.updateStatusAndStatusHistory(statement, ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);
            log.info("Successfully updated statement status to APPROVED for statementId: {}", statementId);
        } else {
            log.error("Failed to calculate CreditDto for ScoringDataDto: {}", scoringDataDto);
        }
    }

    private void fillScoringDataDtoByStatementClient(ScoringDataDto scoringDataDto, Client client) {
        log.debug("Start filling ScoringDataDto with client data. Client: {}", client);

        scoringDataDto.setBirthdate(client.getBirthdate());
        scoringDataDto.setFirstName(client.getFirstName());
        scoringDataDto.setLastName(client.getLastName());
        scoringDataDto.setPassportNumber(client.getPassport().getNumber());
        scoringDataDto.setPassportSeries(client.getPassport().getSeries());

        if (client.getMiddleName() != null) {
            scoringDataDto.setMiddleName(client.getMiddleName());
            log.debug("Set middleName in ScoringDataDto: {}", client.getMiddleName());
        }

        log.info("Successfully filled ScoringDataDto with client data: {}", scoringDataDto);
    }

    public void sendDocuments(String statementId) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .text("Запрос на отправку документов")
                .theme(EmailTheme.SEND_DOCUMENTS)
                .address("ratmuh2809@gmail.com")
                .statementId(statement.getStatementId())
                .build();
        kafkaTemplate.send("send-documents", emailMessageDto);
    }

    public void signDocuments(String statementId) {
        Statement statement = statementService.getStatementById(UUID.fromString(statementId));
        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .text("Запрос на подписание документов")
                .theme(EmailTheme.SIGN_DOCUMENTS)
                .address("ratmuh2809@gmail.com")
                .statementId(statement.getStatementId())
                .build();
        kafkaTemplate.send("sign-documents", emailMessageDto);
    }
}
