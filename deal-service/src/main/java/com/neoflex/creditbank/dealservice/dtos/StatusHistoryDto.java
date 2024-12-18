package com.neoflex.creditbank.dealservice.dtos;

import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import com.neoflex.creditbank.dealservice.enums.ChangeType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StatusHistoryDto {
    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
