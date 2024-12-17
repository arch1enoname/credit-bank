package com.neoflex.creditbank.dealservice.dtos;

import com.neoflex.creditbank.dealservice.enums.Gender;
import com.neoflex.creditbank.dealservice.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FinishRegistrationRequestDto {

    @NotNull(message = "gender cannot be Null")
    @Schema(description = "Пол клиента", example = "MALE")
    private Gender gender;

    @NotNull(message = "maritalStatus cannot be Null")
    @Schema(description = "Семейное положение клиента", example = "MARRIED")
    private MaritalStatus maritalStatus;

    @NotNull(message = "dependentAmount cannot be Null")
    @Min(value = 0, message = "dependentAmount must be at least 0")
    @Schema(description = "Количество иждивенцев", example = "2")
    private Integer dependentAmount;

    @NotNull(message = "passportIssueDate cannot be Null")
    @PastOrPresent(message = "passportIssueDate must be in the past or present")
    @Schema(description = "Дата выдачи паспорта", example = "2020-05-15")
    private LocalDate passportIssueDate;

    @NotBlank(message = "passportIssueBranch cannot be Blank")
    @Schema(description = "Отделение, выдавшее паспорт", example = "770-001")
    private String passportIssueBranch;

    @NotNull(message = "employment cannot be Null")
    @Schema(description = "Данные о трудоустройстве")
    private EmploymentDto employment;

    @NotBlank(message = "accountNumber cannot be Blank")
    @Pattern(regexp = "\\d{20}", message = "accountNumber must be exactly 20 digits")
    @Schema(description = "Номер банковского счета", example = "40817810099910004321")
    private String accountNumber;
}
