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
public class ScoringDataEmploymentStatusValidatorTest {

    @Autowired
    ScoringDataValidator scoringDataValidator;

    ScoringDataDto testData = ScoringDataDto.builder()
            .amount(BigDecimal.valueOf(500000.0))
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
                    .employmentPosition(EmploymentPosition.OWNER)
                    .workExperienceTotal(10)
                    .workExperienceCurrent(5)
                    .build())
            .accountNumber("40817810099910004312")
            .isInsuranceEnabled(true)
            .isSalaryClient(true)
            .build();

    CreditDto creditDto = new CreditDto();

    @Test
    void employmentStatusSelfEmployedValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(14), creditDto.getRate());
    }

    @Test
    void employmentStatusEmployedValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentStatus(EmploymentStatus.EMPLOYED);
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(12), creditDto.getRate());
    }

    @Test
    void employmentStatusUnemployedValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
        assertThrows(
            CalculatorException.class,
            () -> scoringDataValidator.validate(testData, creditDto)
        );
    }

    @Test
    void employmentStatusBusinessOwnerValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(13), creditDto.getRate());
    }

    @Test
    void employmentStatusNullValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentStatus(null);
        assertThrows(
            NullPointerException.class,
            () -> scoringDataValidator.validate(testData, creditDto)
        );
    }
}
