package com.neoflex.creditbank.dealservice.controler;

import com.neoflex.creditbank.dealservice.dtos.FinishRegistrationRequestDto;
import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import com.neoflex.creditbank.dealservice.entities.Statement;
import com.neoflex.creditbank.dealservice.services.impls.DealServiceImpl;
import com.neoflex.creditbank.dealservice.services.impls.StatementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/deal")
@Tag(name = "Deal Controller", description = "Контроллер для управления кредитными предложениями и расчетами")
public class DealController {

    private final DealServiceImpl dealService;
    private final StatementServiceImpl statementService;

    @Autowired
    public DealController(DealServiceImpl dealService, StatementServiceImpl statementService) {
        this.dealService = dealService;
        this.statementService = statementService;
    }

    @Operation(
            summary = "Создание заявки на кредит",
            description = "Генерирует кредитные предложения на основе предоставленных данных"
    )
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> statement(
            @RequestBody
            @Validated
            @Parameter(description = "Данные для расчета кредитных предложений", required = true)
            LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start calculate loanOffers with loanStatementRequestDto: {}", loanStatementRequestDto);
        return ResponseEntity.ok(dealService.getLoanOffers(loanStatementRequestDto));
    }

    @Operation(
            summary = "Выбор кредитного предложения",
            description = "Позволяет выбрать конкретное кредитное предложение"
    )
    @PostMapping("/offer/select")
    public ResponseEntity<Void> selectLoanOffer(
            @RequestBody
            @Validated
            @Parameter(description = "Выбранное кредитное предложение", required = true)
            LoanOfferDto loanOfferDto) {
        log.info("Select loanOffer: {}", loanOfferDto);
        dealService.selectLoanOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Полный расчет кредита",
            description = "Производит полный расчет параметров кредита на основе завершенных регистрационных данных"
    )
    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculate(
            @RequestBody
            @Validated
            @Parameter(description = "Данные для завершения расчета кредита", required = true)
            FinishRegistrationRequestDto finishRegistrationRequestDto,
            @PathVariable("statementId")
            @Parameter(description = "Идентификатор заявки на кредит", required = true)
            String statementId) {
        log.info("Start calculate loanOffer: {}", finishRegistrationRequestDto);
        dealService.calculateLoan(finishRegistrationRequestDto, statementId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Отправка документов",
            description = "Отправляет документы для заявки на кредит по указанному идентификатору"
    )
    @PostMapping("/document/{statementId}/send")
    public ResponseEntity<Void> sendDocuments(
            @PathVariable
            @Parameter(description = "Идентификатор заявки на кредит", required = true)
            String statementId) {
        dealService.sendDocuments(statementId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Подписание документов",
            description = "Подписывает документы для заявки на кредит по указанному идентификатору"
    )
    @PostMapping("/document/{statementId}/sign")
    public ResponseEntity<Void> signDocuments(
            @PathVariable
            @Parameter(description = "Идентификатор заявки на кредит", required = true)
            String statementId) {
        dealService.signDocuments(statementId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение всех заявок",
            description = "Возвращает список всех кредитных заявок (только для администратора)"
    )
    @GetMapping("/admin/statement")
    public ResponseEntity<List<Statement>> getAllStatement() {
        return ResponseEntity.ok(statementService.getAllStatements());
    }

    @Operation(
            summary = "Получение заявки по ID",
            description = "Возвращает информацию о кредитной заявке по её идентификатору (только для администратора)"
    )
    @GetMapping("/admin/statement/{statementId}")
    public ResponseEntity<Statement> getStatementById(
            @PathVariable
            @Parameter(description = "Идентификатор заявки на кредит", required = true)
            String statementId) {
        return ResponseEntity.ok(statementService.getStatementById(UUID.fromString(statementId)));
    }
}
