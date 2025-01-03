package com.arthur.statementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanOfferDto {

    @NotNull(message = "statementId cannot be Null")
    @Schema(description = "Идентификатор заявки на кредит", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID statementId;

    @NotNull(message = "requestedAmount cannot be Null")
    @DecimalMin(value = "0.01", message = "requestedAmount must be at least 0.01")
    @Schema(description = "Запрашиваемая сумма кредита", example = "500000.0")
    private BigDecimal requestedAmount;

    @NotNull(message = "totalAmount cannot be Null")
    @DecimalMin(value = "0.01", message = "totalAmount must be at least 0.01")
    @Schema(description = "Общая сумма выплаты по кредиту", example = "540000.0")
    private BigDecimal totalAmount;

    @NotNull(message = "term cannot be Null")
    @Min(value = 1, message = "term must be at least 1")
    @Schema(description = "Срок кредита в месяцах", example = "12")
    private Integer term;

    @NotNull(message = "monthlyPayment cannot be Null")
    @DecimalMin(value = "0.01", message = "monthlyPayment must be at least 0.01")
    @Schema(description = "Ежемесячный платеж по кредиту", example = "45000.0")
    private BigDecimal monthlyPayment;

    @NotNull(message = "rate cannot be Null")
    @DecimalMin(value = "0.01", message = "rate must be at least 0.01")
    @Schema(description = "Годовая процентная ставка", example = "5.0")
    private BigDecimal rate;

    @NotNull(message = "isInsuranceEnabled cannot be Null")
    @Schema(description = "Флаг наличия страховки", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be Null")
    @Schema(description = "Флаг зарплатного клиента", example = "true")
    private Boolean isSalaryClient;
}
