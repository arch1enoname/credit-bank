import com.arthur.calculator.utils.LoanCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.math.RoundingMode;


@WebMvcTest(LoanCalculator.class)
@Import(LoanCalculator.class)
class LoanCalculatorTest {

    @Value("${insurance.cost}")
    private BigDecimal INSURANCE_COST;
    @Value("${insurance.discount}")
    private BigDecimal INSURANCE_DISCOUNT;
    @Value("${salary.client.discount}")
    private BigDecimal SALARY_CLIENT_DISCOUNT;

    @Autowired
    private LoanCalculator loanCalculator;

    @Test
    void calculateMonthlyPayment_shouldReturnMonthlyPayment() {
        BigDecimal loanAmount = BigDecimal.valueOf(100000);
        BigDecimal interestRate = BigDecimal.valueOf(10.5);
        int term = 12;

        BigDecimal monthlyPayment = loanCalculator.calculateMonthlyPayment(loanAmount, interestRate, term);
        Assertions.assertEquals(BigDecimal.valueOf(8814.86).setScale(2, RoundingMode.HALF_UP), monthlyPayment);
    }

    @Test
    void calculatePrincipal_shouldReturnPrincipal() {
        BigDecimal amount = BigDecimal.valueOf(100000);
        boolean isInsuranceEnabled = true;

        BigDecimal principal = loanCalculator.calculatePrincipal(amount, isInsuranceEnabled);
        Assertions.assertEquals(BigDecimal.valueOf(100000).add(INSURANCE_COST), principal);
    }

    @Test
    void adjustInterestRate_shouldAdjustInterestRate() {
        BigDecimal interestRate = BigDecimal.valueOf(10.5);
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;

        BigDecimal adjustedRate = loanCalculator.adjustInterestRate(interestRate, isInsuranceEnabled, isSalaryClient);
        Assertions.assertEquals(interestRate.subtract(INSURANCE_DISCOUNT).subtract(SALARY_CLIENT_DISCOUNT), adjustedRate);
    }

    @Test
    void calculateTotalAmount_shouldReturnTotalAmount() {
        BigDecimal monthlyPayment = BigDecimal.valueOf(8600);
        int term = 12;

        BigDecimal totalAmount = loanCalculator.calculateTotalAmount(monthlyPayment, term);
        Assertions.assertEquals(monthlyPayment.multiply(BigDecimal.valueOf(term)), totalAmount);
    }
}
