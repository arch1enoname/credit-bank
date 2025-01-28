package com.neoflex.creditbank.dealservice.dtos;

import com.neoflex.creditbank.dealservice.enums.EmailTheme;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailMessageDto {
    String address;
    EmailTheme theme;
    UUID statementId;
    String text;
}
