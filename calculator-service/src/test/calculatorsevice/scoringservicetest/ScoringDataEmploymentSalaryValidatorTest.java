package calculatorsevice.scoringservicetest;

import com.arthur.calculator.CalculatorApplication;
import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.EmploymentDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.enums.EmploymentPosition;
import com.arthur.calculator.enums.EmploymentStatus;
import com.arthur.calculator.enums.Gender;
import com.arthur.calculator.enums.MaritalStatus;
import com.arthur.calculator.exceptions.CalculatorException;
import com.arthur.calculator.utils.ScoringDataValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CalculatorApplication.class)
public class ScoringDataEmploymentSalaryValidatorTest {
    @Autowired
    ScoringDataValidator scoringDataValidator;

    ScoringDataDto testData = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(50000.0))
            .term(12)
            .firstName("Артур")
            .lastName("Булатов")
            .middleName("Булатович")
            .gender(Gender.MALE)
            .birthdate(LocalDate.of(1990, 5, 1))
            .passportSeries("1234")
            .passportNumber("567890")
            .passportIssueDate(LocalDate.of(2010, 4, 15))
            .passportIssueBranch("Отдел УФМС России по Москве")
            .maritalStatus(MaritalStatus.MARRIED)
            .dependentAmount(2)
            .employment(EmploymentDto.builder()
                    .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                    .employerINN("7701234567")
                    .salary(BigDecimal.valueOf(100000.0))
                    .employmentPosition(EmploymentPosition.MID_MANAGER)
                    .workExperienceTotal(10)
                    .workExperienceCurrent(5)
                    .build())
            .accountNumber("40817810099910004312")
            .isInsuranceEnabled(true)
            .isSalaryClient(true)
            .build();

    CreditDto creditDto = new CreditDto();

    @Test
    void employmentSalaryNormalRatioValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(17), creditDto.getRate());
    }

    @Test
    void employmentSalaryNotNormalRatioValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.setAmount(BigDecimal.valueOf(20000000));

        assertThrows(
                CalculatorException.class,
                () -> scoringDataValidator.validate(testData, creditDto)
        );
    }
}
