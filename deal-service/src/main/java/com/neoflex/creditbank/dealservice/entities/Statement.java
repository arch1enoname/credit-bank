package com.neoflex.creditbank.dealservice.entities;

import com.neoflex.creditbank.dealservice.dtos.LoanOfferDto;
import com.neoflex.creditbank.dealservice.dtos.StatusHistoryDto;
import com.neoflex.creditbank.dealservice.enums.ApplicationStatus;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statement")
public class Statement {

    @Id
    @Column(name = "statement_id")
    @UuidGenerator
    private UUID statementId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @Type(value = JsonBinaryType.class)
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private Integer sesCode;

    @Type(value = JsonBinaryType.class)
    @Column(name = "status_history", columnDefinition = "jsonb")
    private List<StatusHistoryDto> statusHistory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Statement statement = (Statement) o;
        return statementId != null && Objects.equals(statementId, statement.statementId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
