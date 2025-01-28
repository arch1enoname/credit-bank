import com.arthur.calculator.CalculatorApplication;
import com.arthur.calculator.utils.LoanCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CalculatorApplication.class)
public class LoanCalculatorTest {
    @Autowired
    LoanCalculator loanCalculator;

    @Test
    void calculateTotalAmount_shouldReturnCorrectResultForValidInput() {
        // Arrange
        BigDecimal monthlyPayment = BigDecimal.valueOf(1000);
        int term = 12;

        // Act
        BigDecimal result = loanCalculator.calculateTotalAmount(monthlyPayment, term);

        // Assert
        assertEquals(BigDecimal.valueOf(12000), result, "The total amount should be calculated correctly.");
    }

    @Test
    void calculateTotalAmount_shouldReturnZeroIfMonthlyPaymentIsZero() {
        // Arrange
        BigDecimal monthlyPayment = BigDecimal.ZERO;
        int term = 12;

        // Act
        BigDecimal result = loanCalculator.calculateTotalAmount(monthlyPayment, term);

        // Assert
        assertEquals(BigDecimal.ZERO, result, "The total amount should be zero if the monthly payment is zero.");
    }

    @Test
    void calculateTotalAmount_shouldReturnZeroIfTermIsZero() {
        // Arrange
        BigDecimal monthlyPayment = BigDecimal.valueOf(1000);
        int term = 0;

        // Act
        BigDecimal result = loanCalculator.calculateTotalAmount(monthlyPayment, term);

        // Assert
        assertEquals(BigDecimal.ZERO, result, "The total amount should be zero if the term is zero.");
    }

    @Test
    void calculateTotalAmount_shouldHandleLargeValues() {
        // Arrange
        BigDecimal monthlyPayment = BigDecimal.valueOf(1_000_000);
        int term = 120; // 10 years

        // Act
        BigDecimal result = loanCalculator.calculateTotalAmount(monthlyPayment, term);

        // Assert
        assertEquals(BigDecimal.valueOf(120_000_000), result, "The total amount should be correctly calculated for large values.");
    }

    @Test
    void calculateTotalAmount_shouldHandlePrecision() {
        // Arrange
        BigDecimal monthlyPayment = new BigDecimal("1234.56");
        int term = 12;

        // Act
        BigDecimal result = loanCalculator.calculateTotalAmount(monthlyPayment, term);

        // Assert
        assertEquals(new BigDecimal("14814.72"), result, "The total amount should handle decimal precision correctly.");
    }

    @Test
    void adjustInterestRate_shouldApplyNoDiscounts() {
        // Arrange
        BigDecimal interestRate = BigDecimal.valueOf(10.0);
        boolean isInsuranceEnabled = false;
        boolean isSalaryClient = false;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(10.0), result, "The interest rate should remain unchanged if no discounts are applied.");
    }

    @Test
    void adjustInterestRate_shouldApplyInsuranceDiscount() {
        // Arrange
        BigDecimal interestRate = BigDecimal.valueOf(10.0);
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = false;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(7.0), result, "The interest rate should be reduced by the insurance discount.");
    }

    @Test
    void adjustInterestRate_shouldApplySalaryClientDiscount() {
        // Arrange
        BigDecimal interestRate = BigDecimal.valueOf(10.0);
        boolean isInsuranceEnabled = false;
        boolean isSalaryClient = true;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(9.0), result, "The interest rate should be reduced by the salary client discount.");
    }

    @Test
    void adjustInterestRate_shouldApplyBothDiscounts() {
        // Arrange
        BigDecimal interestRate = BigDecimal.valueOf(10.0);
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(6.0), result, "The interest rate should be reduced by both discounts.");
    }

    @Test
    void adjustInterestRate_shouldHandleZeroInterestRate() {
        // Arrange
        BigDecimal interestRate = BigDecimal.ZERO;
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(-4), result, "The interest rate can go negative if discounts exceed the original rate.");
    }

    @Test
    void adjustInterestRate_shouldHandleNegativeInterestRate() {
        // Arrange
        BigDecimal interestRate = BigDecimal.valueOf(-5.0);
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(BigDecimal.valueOf(-9.0), result, "The method should handle negative interest rates.");
    }

    @Test
    void adjustInterestRate_shouldHandlePrecision() {
        // Arrange
        BigDecimal interestRate = new BigDecimal("10.123");
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;

        // Act
        BigDecimal result = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);

        // Assert
        assertEquals(new BigDecimal("6.123"), result, "The method should correctly handle precision for decimal values.");
    }

    @Test
    void calculatePrincipal_shouldReturnCorrectAmountWithInsurance() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100000);
        boolean isInsuranceEnabled = true;

        // Act
        BigDecimal result = loanCalculator.calculatePrincipal(amount, isInsuranceEnabled);

        // Assert
        assertEquals(BigDecimal.valueOf(200000), result, "Principal should include insurance cost when insurance is enabled.");
    }

    @Test
    void calculatePrincipal_shouldReturnCorrectAmountWithoutInsurance() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100000);
        boolean isInsuranceEnabled = false;

        // Act
        BigDecimal result = loanCalculator.calculatePrincipal(amount, isInsuranceEnabled);

        // Assert
        assertEquals(BigDecimal.valueOf(100000), result, "Principal should not include insurance cost when insurance is disabled.");
    }

    @Test
    void calculatePrincipal_shouldHandleZeroAmount() {
        // Arrange
        BigDecimal amount = BigDecimal.ZERO;
        boolean isInsuranceEnabled = true;

        // Act
        BigDecimal result = loanCalculator.calculatePrincipal(amount, isInsuranceEnabled);

        // Assert
        assertEquals(BigDecimal.valueOf(100000), result, "Principal should equal the insurance cost if the base amount is zero and insurance is enabled.");
    }

    @Test
    void calculatePrincipal_shouldThrowExceptionForNullAmount() {
        // Arrange
        BigDecimal amount = null;
        boolean isInsuranceEnabled = true;

        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> loanCalculator.calculatePrincipal(amount, isInsuranceEnabled),
                "Method should throw a NullPointerException if amount is null."
        );
    }

}
