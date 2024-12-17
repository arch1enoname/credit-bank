package com.neoflex.creditbank.dealservice.services;

import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import com.neoflex.creditbank.dealservice.entities.Client;

public interface ClientService {
    Client createClient(LoanStatementRequestDto request);
}
