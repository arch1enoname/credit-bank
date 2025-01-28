import com.arthur.calculator.CalculatorApplication;
import com.arthur.calculator.dtos.CreditDto;
import com.arthur.calculator.dtos.EmploymentDto;
import com.arthur.calculator.dtos.PaymentScheduleElementDto;
import com.arthur.calculator.dtos.ScoringDataDto;
import com.arthur.calculator.enums.EmploymentPosition;
import com.arthur.calculator.enums.EmploymentStatus;
import com.arthur.calculator.enums.Gender;
import com.arthur.calculator.enums.MaritalStatus;
import com.arthur.calculator.exceptions.CalculatorException;
import com.arthur.calculator.services.impl.CalculatorServiceImpl;
import com.arthur.calculator.utils.ScoringDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorServiceTest {


    @Mock
    private ScoringDataValidator scoringDataValidator;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculate_ValidScoringData() throws CalculatorException {
        // Arrange
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(500000));
        scoringDataDto.setTerm(12);
        scoringDataDto.setFirstName("Артур");
        scoringDataDto.setLastName("Булатов");
        scoringDataDto.setGender(Gender.MALE);
        scoringDataDto.setBirthdate(LocalDate.of(1990, 5, 1));
        scoringDataDto.setPassportSeries("1234");
        scoringDataDto.setPassportNumber("567890");
        scoringDataDto.setPassportIssueDate(LocalDate.of(2010, 4, 15));
        scoringDataDto.setPassportIssueBranch("Отдел УФМС России по Москве");
        scoringDataDto.setMaritalStatus(MaritalStatus.MARRIED);
        scoringDataDto.setDependentAmount(2);
        scoringDataDto.setEmployment(EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                .employerINN("7701234567")
                .salary(BigDecimal.valueOf(100000.0))
                .employmentPosition(EmploymentPosition.MID_MANAGER)
                .workExperienceTotal(10)
                .workExperienceCurrent(5)
                .build());
        scoringDataDto.setAccountNumber("40817810099910004312");
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);

        // Act
        CreditDto creditDto = calculatorService.calculate(scoringDataDto);

        // Assert
        assertNotNull(creditDto);
        assertEquals(BigDecimal.valueOf(500000), creditDto.getAmount());
        assertEquals(12, creditDto.getTerm());
        assertEquals(true, creditDto.getIsInsuranceEnabled());
        assertEquals(true, creditDto.getIsSalaryClient());
        assertNotNull(creditDto.getMonthlyPayment());
        assertNotNull(creditDto.getPaymentSchedule());
        assertNotNull(creditDto.getPsk());
    }

    @Test
    void testCalculate_WithoutInsurance() throws CalculatorException {
        // Arrange
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(300000));
        scoringDataDto.setTerm(24);
        scoringDataDto.setIsInsuranceEnabled(false);
        scoringDataDto.setIsSalaryClient(false);

        // Act
        CreditDto creditDto = calculatorService.calculate(scoringDataDto);

        // Assert
        assertNotNull(creditDto);
        assertEquals(false, creditDto.getIsInsuranceEnabled());
        assertEquals(false, creditDto.getIsSalaryClient());
        assertNotNull(creditDto.getMonthlyPayment());
    }

    @Test
    void testCalculate_WithSalaryClient() throws CalculatorException {
        // Arrange
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(400000));
        scoringDataDto.setTerm(36);
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);

        // Act
        CreditDto creditDto = calculatorService.calculate(scoringDataDto);

        // Assert
        assertNotNull(creditDto);
        assertEquals(true, creditDto.getIsSalaryClient());
        assertNotNull(creditDto.getPaymentSchedule());
    }



}
