package com.arthur.calculator.enums.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanStatementRequestDto {

    @NotNull(message = "amount cannot be Null")
    @DecimalMin(value = "20000.0")
    @Schema(description = "Сумма кредита", example = "500000.0")
    private BigDecimal amount;

    @NotNull(message = "term cannot be Null")
    @Min(value = 6, message = "term must be at least 6")
    @Schema(description = "Количество месяцев кредита", example = "12")
    private Integer term;

    @NotNull(message = "firstName cannot be Null")
    @Size(min = 2, max = 60, message = "firstName must be between 2 and 60 characters")
    @Schema(description = "Имя клиента", example = "Артур")
    private String firstName;

    @NotNull(message = "lastName cannot be Null")
    @Size(min = 2, max = 60, message = "lastName must be between 2 and 60 characters")
    @Schema(description = "Фамилия клиента", example = "Булатов")
    private String lastName;

    @Size(min = 2, max = 60, message = "middleName must be between 2 and 60 characters")
    @Schema(description = "Отчество клиента", example = "Булатович")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Schema(description = "Электронная почти", example = "pochta@mail.ru")
    private String email;

    @Past(message = "birthDate must be in the past")
    @Pattern(regexp = "yyyy-MM-dd", message = "Invalid birthdate")
    @Schema(description = "Дата рождения", example = "1990-01-01")
    private LocalDate birthdate;

    @NotNull(message = "passportSeries cannot be Null")
    @Pattern(regexp = "\\d{4}", message = "Invalid passport series")
    @Schema(description = "Серия паспорта", example = "1234")
    private String passportSeries;

    @NotNull(message = "passportNumber cannot be Null")
    @Pattern(regexp = "\\d{6}", message = "Invalid passport number")
    @Schema(description = "Номер паспорта", example = "123456")
    private String passportNumber;
}

