package com.arthur.calculator.enums.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PassportDto {
    @NotNull(message = "passportSeries cannot be Null")
    @Schema(description = "Серия паспорта клиента", example = "1234")
    String series;
    @NotNull(message = "passportSeries cannot be Null")
    @Schema(description = "Номер паспорта клиента", example = "123456")
    String number;
    @NotNull(message = "issueBranch cannot be Null")
    @Schema(description = "Орган, выдавший паспорт", example = "123456")
    String issueBranch;
    @NotNull(message = "issueDate cannot be Null")
    @Schema(description = "Дата окончания паспорта", example = "13.10.2025")
    LocalDate issueDate;
}
