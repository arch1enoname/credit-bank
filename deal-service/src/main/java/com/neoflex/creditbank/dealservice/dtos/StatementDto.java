package com.neoflex.creditbank.dealservice.dtos;

import com.neoflex.creditbank.dealservice.entities.Client;
import com.neoflex.creditbank.dealservice.entities.Credit;
import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StatementDto {
    private Client client;

    private Credit credit;

    private ApplicationStatus applicationStatus;

    private LocalDateTime creationDate;

    private LoanOfferDto appliedOffer;

    private LocalDateTime signDate;

    private Integer sesCode;

    private List<StatusHistoryDto> statusHistory;
}
