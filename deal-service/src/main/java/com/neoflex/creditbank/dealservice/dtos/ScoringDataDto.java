package com.neoflex.creditbank.dealservice.dtos;

import com.neoflex.creditbank.dealservice.enums.Gender;
import com.neoflex.creditbank.dealservice.enums.MaritalStatus;
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
public class ScoringDataDto {

    @NotNull(message = "amount cannot be Null")
    @DecimalMin(value = "0.01", message = "amount must be at least 0.01")
    @Schema(description = "Сумма запрашиваемого кредита", example = "500000.0")
    private BigDecimal amount;

    @NotNull(message = "term cannot be Null")
    @Min(value = 6, message = "term must be at least 6")
    @Schema(description = "Срок кредита в месяцах", example = "12")
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
    @Schema(description = "Отчество клиента (если указано)", example = "Иванович")
    private String middleName;

    @NotNull(message = "gender cannot be Null")
    @Schema(description = "Пол клиента", example = "MALE")
    private Gender gender;

    @NotNull(message = "birthdate cannot be Null")
    @Past(message = "birthdate must be in the past")
    @Schema(description = "Дата рождения клиента", example = "1990-05-01")
    private LocalDate birthdate;

    @NotNull(message = "passportSeries cannot be Null")
    @Schema(description = "Серия паспорта клиента", example = "1234")
    private String passportSeries;

    @NotBlank(message = "passportNumber cannot be Blank")
    @Schema(description = "Номер паспорта клиента", example = "567890")
    private String passportNumber;

    @NotNull(message = "passportIssueDate cannot be Null")
    @Past(message = "passportIssueDate must be in the past")
    @Schema(description = "Дата выдачи паспорта", example = "2010-04-15")
    private LocalDate passportIssueDate;

    @NotBlank(message = "passportIssueBranch cannot be Blank")
    @Schema(description = "Орган, выдавший паспорт", example = "Отдел УФМС России по Москве")
    private String passportIssueBranch;

    @NotNull(message = "maritalStatus cannot be Null")
    @Schema(description = "Семейное положение клиента", example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Min(value = 0, message = "dependentAmount must be at least 0")
    @Schema(description = "Количество иждивенцев у клиента", example = "2")
    private Integer dependentAmount;

    @NotNull(message = "employment cannot be Null")
    @Schema(description = "Данные о трудоустройстве клиента", example = "{...}")
    private EmploymentDto employment;

    @NotNull(message = "accountNumber cannot be Null")
    @Schema(description = "Номер расчетного счета клиента", example = "40817810099910004312")
    private String accountNumber;

    @NotNull(message = "isInsuranceEnabled cannot be Null")
    @Schema(description = "Флаг наличия страховки", example = "true")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient cannot be Null")
    @Schema(description = "Флаг зарплатного клиента", example = "true")
    private Boolean isSalaryClient;
}
