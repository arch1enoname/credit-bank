package calculatorsevice.scoringservicetest;

import com.arthur.calculator.CalculatorApplication;
import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.EmploymentDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.enums.EmploymentPosition;
import com.arthur.calculator.enums.EmploymentStatus;
import com.arthur.calculator.enums.Gender;
import com.arthur.calculator.enums.MaritalStatus;
import com.arthur.calculator.utils.ScoringDataValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CalculatorApplication.class)
public class ScoringDataEmploymentPositionValidatorTest {
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
    void employmentPositionMidManagerValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(17), creditDto.getRate());
    }
    @Test
    void employmentPositionTopManagerValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentPosition(EmploymentPosition.TOP_MANAGER);
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(16), creditDto.getRate());
    }
    @Test
    void employmentPositionOwnerValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentPosition(EmploymentPosition.OWNER);

        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(14), creditDto.getRate());
    }
    @Test
    void employmentPositionWorkerValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentPosition(EmploymentPosition.WORKER);
        scoringDataValidator.validate(testData, creditDto);

        assertEquals(BigDecimal.valueOf(18), creditDto.getRate());
    }

    @Test
    void employmentPositionNullValidatorTest() {
        creditDto.setRate(BigDecimal.valueOf(20));
        testData.getEmployment().setEmploymentPosition(null);
        assertThrows(
                NullPointerException.class,
                () -> scoringDataValidator.validate(testData, creditDto)
        );
    }

}
