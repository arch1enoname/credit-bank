package com.neoflex.creditbank.dealservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.CreditDto;
import com.neoflex.creditbank.dealservice.entities.Credit;
import com.neoflex.creditbank.dealservice.enums.CreditStatus;
import com.neoflex.creditbank.dealservice.repositories.CreditRepository;
import com.neoflex.creditbank.dealservice.services.CreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit createCredit(CreditDto creditDto) {
        log.info("Start creating credit with CreditDto: {}", creditDto);

        Credit credit = Credit.builder()
                .amount(creditDto.getAmount())
                .term(creditDto.getTerm())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .rate(creditDto.getRate())
                .psk(creditDto.getPsk())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .isSalaryClient(creditDto.getIsSalaryClient())
                .isInsuranceEnabled(creditDto.getIsInsuranceEnabled())
                .creditStatus(CreditStatus.CALCULATED)
                .build();

        log.debug("Built Credit object before saving: {}", credit);

        Credit savedCredit = creditRepository.save(credit);

        log.info("Successfully saved Credit: {}", savedCredit);
        return savedCredit;
    }
}
