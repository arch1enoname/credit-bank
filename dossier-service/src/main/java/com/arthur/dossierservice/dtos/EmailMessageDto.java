package com.arthur.dossierservice.dtos;

import com.arthur.dossierservice.enums.EmailTheme;
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
