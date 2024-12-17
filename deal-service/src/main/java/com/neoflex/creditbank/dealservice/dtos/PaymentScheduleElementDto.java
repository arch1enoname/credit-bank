package com.neoflex.creditbank.dealservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentScheduleElementDto {

    @NotNull(message = "number cannot be Null")
    @Schema(description = "Номер платежа в графике", example = "1")
    private Integer number;

    @NotNull(message = "date cannot be Null")
    @Schema(description = "Дата платежа", example = "2024-01-01")
    private LocalDate date;

    @NotNull(message = "totalPayment cannot be Null")
    @DecimalMin(value = "0.01", message = "totalPayment must be at least 0.01")
    @Schema(description = "Общая сумма платежа", example = "45000.0")
    private BigDecimal totalPayment;

    @NotNull(message = "interestPayment cannot be Null")
    @DecimalMin(value = "0.01", message = "interestPayment must be at least 0.01")
    @Schema(description = "Сумма платежа по процентам", example = "5000.0")
    private BigDecimal interestPayment;

    @NotNull(message = "debtPayment cannot be Null")
    @DecimalMin(value = "0.01", message = "debtPayment must be at least 0.01")
    @Schema(description = "Сумма платежа по основному долгу", example = "40000.0")
    private BigDecimal debtPayment;

    @NotNull(message = "remainingDebt cannot be Null")
    @DecimalMin(value = "0.00", message = "remainingDebt cannot be negative")
    @Schema(description = "Оставшаяся задолженность после платежа", example = "460000.0")
    private BigDecimal remainingDebt;
}


