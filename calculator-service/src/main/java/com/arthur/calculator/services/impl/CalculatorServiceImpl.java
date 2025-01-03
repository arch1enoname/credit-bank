package com.arthur.calculator.services.impl;

import com.arthur.calculator.dtos.*;
import com.arthur.calculator.exceptions.CalculatorException;
import com.arthur.calculator.services.CalculatorService;
import com.arthur.calculator.utils.LoanCalculator;
import com.arthur.calculator.utils.ScoringDataValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Value("${base.interest.rate}")
    private BigDecimal BASE_INTEREST_RATE;
    private final ScoringDataValidator scoringDataValidator;
    private final LoanCalculator loanCalculator;


    @Autowired
    public CalculatorServiceImpl(ScoringDataValidator scoringDataValidator, LoanCalculator loanCalculator) {
        this.scoringDataValidator = scoringDataValidator;
        this.loanCalculator = loanCalculator;
    }

    public CreditDto calculate(ScoringDataDto scoringDataDto) throws CalculatorException {
        log.info("calculating scoring data");
        CreditDto creditDto = CreditDto.builder()
                .rate(BASE_INTEREST_RATE)
                .term(scoringDataDto.getTerm())
                .amount(scoringDataDto.getAmount())
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .build();

        scoringDataValidator.validate(scoringDataDto, creditDto);


        creditDto.setMonthlyPayment(calculateMonthlyPayment(scoringDataDto, creditDto));
        creditDto.setPaymentSchedule(generatePaymentSchedule(creditDto));
        creditDto.setPsk(creditDto.getMonthlyPayment().multiply(BigDecimal.valueOf(scoringDataDto.getTerm())));
        return creditDto;
    }

    @Override
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequestDto) throws CalculatorException {
        log.info("starting calculating loan offers");
        return Stream.of(true, false)
                .flatMap(isSalaryClient -> Stream.of(true, false)
                        .map(isInsuranceEnabled -> createLoanOffer(loanStatementRequestDto, isSalaryClient, isInsuranceEnabled)))
                .sorted(Comparator.comparing(LoanOfferDto::getTotalAmount))
                .collect(Collectors.toList());
    }

    private LoanOfferDto createLoanOffer(LoanStatementRequestDto loanStatement, Boolean isSalaryClient, Boolean isInsuranceEnabled) {
        log.debug("Creating loan offer with parameters: isSalaryClient={}, isInsuranceEnabled={}", isSalaryClient, isInsuranceEnabled);

        BigDecimal loanAmount = loanStatement.getAmount();

        BigDecimal interestRate = loanCalculator.adjustInterestRate(BASE_INTEREST_RATE, isInsuranceEnabled, isSalaryClient);

        BigDecimal principal = loanCalculator.calculatePrincipal(loanAmount, isInsuranceEnabled);
        BigDecimal monthlyPayment = loanCalculator.calculateMonthlyPayment(principal, interestRate, loanStatement.getTerm());
        BigDecimal totalAmount = loanCalculator.calculateTotalAmount(monthlyPayment, loanStatement.getTerm());

        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .term(loanStatement.getTerm())
                .requestedAmount(loanAmount)
                .monthlyPayment(monthlyPayment)
                .rate(interestRate)
                .totalAmount(totalAmount)
                .isSalaryClient(isSalaryClient)
                .isInsuranceEnabled(isInsuranceEnabled)
                .build();
    }

    public BigDecimal calculateMonthlyPayment(ScoringDataDto scoringDataDto, CreditDto creditDto) {
        log.debug("calculating monthly payment for scoring data");
        BigDecimal annualRate = creditDto.getRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP); // Перевод процентов в доли
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP); // Годовая ставка -> месячная
        BigDecimal onePlusRatePowN = monthlyRate.add(BigDecimal.ONE).pow(scoringDataDto.getTerm());
        BigDecimal numerator = monthlyRate.multiply(onePlusRatePowN);
        BigDecimal denominator = onePlusRatePowN.subtract(BigDecimal.ONE);
        return scoringDataDto.getAmount()
                .multiply(numerator)
                .divide(denominator, 2, RoundingMode.HALF_UP);
    }


    private List<PaymentScheduleElementDto> generatePaymentSchedule(CreditDto creditDto) {
        log.debug("generating payment schedule for scoring data");
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        BigDecimal remainingDebt = creditDto.getAmount();
        BigDecimal totalPayment = creditDto.getMonthlyPayment();
        BigDecimal interestPayment;
        BigDecimal debtPayment;
        BigDecimal rate = creditDto.getRate();
        Integer term = creditDto.getTerm();

        for (int i = 1; i <= term; i++) {
            startDate = startDate.plusMonths(1);
            interestPayment = calculateInterestPayment(remainingDebt, rate);
            debtPayment = calculateDebtPayment(totalPayment, interestPayment);
            remainingDebt = calculateRemainingDebt(remainingDebt, debtPayment);

            if (i == term) {
                totalPayment = totalPayment.add(remainingDebt);
                remainingDebt = BigDecimal.valueOf(0);
            }

            paymentSchedule.add(
                    PaymentScheduleElementDto.builder()
                            .number(i)
                            .date(startDate)
                            .totalPayment(totalPayment.setScale(2, RoundingMode.HALF_UP))
                            .interestPayment(interestPayment.setScale(2, RoundingMode.HALF_UP))
                            .debtPayment(debtPayment.setScale(2, RoundingMode.HALF_UP))
                            .remainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_UP))
                            .build()
            );
        }
        return paymentSchedule;
    }

    private BigDecimal calculateInterestPayment(BigDecimal remainingDebt, BigDecimal rate) {
        return remainingDebt.multiply(rate.divide(BigDecimal.valueOf(12 * 100), 7, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDebtPayment(BigDecimal totalPayment, BigDecimal interestPayment) {
        return totalPayment.subtract(interestPayment).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRemainingDebt(BigDecimal remainingDebt, BigDecimal debtPayment) {
        return remainingDebt.subtract(debtPayment).setScale(2, RoundingMode.HALF_UP);
    }
}
