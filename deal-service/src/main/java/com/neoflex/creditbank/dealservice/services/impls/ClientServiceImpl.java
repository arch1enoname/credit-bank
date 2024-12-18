package com.neoflex.creditbank.dealservice.services.impls;

import com.neoflex.creditbank.dealservice.dtos.LoanStatementRequestDto;
import com.neoflex.creditbank.dealservice.dtos.PassportDto;
import com.neoflex.creditbank.dealservice.entities.Client;
import com.neoflex.creditbank.dealservice.repositories.ClientRepository;
import com.neoflex.creditbank.dealservice.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start creating client with LoanStatementRequestDto: {}", loanStatementRequestDto);

        PassportDto passportDto = createPassportDto(loanStatementRequestDto);
        log.debug("Created PassportDto: {}", passportDto);

        Client client = Client.builder()
                .email(loanStatementRequestDto.getEmail())
                .passport(passportDto)
                .birthdate(loanStatementRequestDto.getBirthdate())
                .firstName(loanStatementRequestDto.getFirstName())
                .lastName(loanStatementRequestDto.getLastName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .build();

        log.info("Successfully created Client: {}", client);
        return client;
    }

    private PassportDto createPassportDto(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start creating PassportDto with LoanStatementRequestDto: {}", loanStatementRequestDto);

        PassportDto passportDto = PassportDto.builder()
                .number(loanStatementRequestDto.getPassportNumber())
                .series(loanStatementRequestDto.getPassportSeries())
                .build();

        log.debug("Successfully created PassportDto: {}", passportDto);
        return passportDto;
    }
}
