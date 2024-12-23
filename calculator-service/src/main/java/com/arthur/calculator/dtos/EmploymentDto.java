package com.arthur.calculator.dtos;

import com.arthur.calculator.enums.EmploymentPosition;
import com.arthur.calculator.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmploymentDto {

    @NotNull(message = "employmentStatus cannot be Null")
    @Schema(description = "Статус занятости клиента", example = "EMPLOYED")
    private EmploymentStatus employmentStatus;

    @NotBlank(message = "employerINN cannot be Blank")
    @Schema(description = "ИНН работодателя", example = "123456789012")
    private String employerINN;

    @NotNull(message = "salary cannot be Null")
    @DecimalMin(value = "0.01", message = "salary must be at least 0.01")
    @Schema(description = "Размер заработной платы", example = "75000.0")
    private BigDecimal salary;

    @NotNull(message = "position cannot be Null")
    @Schema(description = "Должность клиента", example = "ENGINEER")
    private EmploymentPosition employmentPosition;

    @Min(value = 0, message = "workExperienceTotal must be at least 0")
    @Schema(description = "Общий стаж работы (в месяцах)", example = "120")
    private Integer workExperienceTotal;

    @Min(value = 0, message = "workExperienceCurrent must be at least 0")
    @Schema(description = "Стаж работы на текущем месте (в месяцах)", example = "24")
    private Integer workExperienceCurrent;
}


