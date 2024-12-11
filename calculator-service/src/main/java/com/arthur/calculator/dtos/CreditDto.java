package com.arthur.calculator.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditDto {

    @NotNull(message = "amount cannot be Null")
    @DecimalMin(value = "0.01")
    @Schema(description = "Сумма кредита", example = "500000.0")
    private BigDecimal amount;

    @NotNull(message = "term cannot be Null")
    @Min(value = 6, message = "term must be at least 6")
    @Schema(description = "Срок кредита", example = "12")
    private Integer term;

    @NotNull(message = "monthlyPayment cannot be Null")
    @DecimalMin(value = "0.01", message = "monthlyPayment must be at least 0.01")
    @Schema(description = "Ежемесячный платеж", example = "45000.0")
    private BigDecimal monthlyPayment;

    @NotNull(message = "rate cannot be Null")
    @DecimalMin(value = "0.01", message = "rate must be at least 0.01")
    @Schema(description = "Процентная ставка по кредиту", example = "12.5")
    private BigDecimal rate;

    @NotNull(message = "psk cannot be Null")
    @DecimalMin(value = "0.01", message = "psk must be at least 0.01")
    @Schema(description = "Полная стоимость кредита (ПСК)", example = "13.2")
    private BigDecimal psk;

    @NotNull(message = "isInsuranceEnabled cannot be Null")
    @Schema(description = "Флаг наличия страховки", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be Null")
    @Schema(description = "Флаг зарплатного клиента", example = "true")
    private Boolean isSalaryClient;

    @NotNull(message = "paymentSchedule cannot be Null")
    @Schema(description = "График платежей", example = "[...]")
    private List<PaymentScheduleElementDto> paymentSchedule;
}


