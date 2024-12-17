package com.neoflex.creditbank.dealservice.services;

import com.neoflex.creditbank.dealservice.dtos.CreditDto;
import com.neoflex.creditbank.dealservice.entities.Credit;

public interface CreditService {
    Credit createCredit(CreditDto creditDto);
}
