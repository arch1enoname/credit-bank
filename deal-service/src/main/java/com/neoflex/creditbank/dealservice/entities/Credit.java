package com.neoflex.creditbank.dealservice.entities;

import com.neoflex.creditbank.dealservice.dtos.PaymentScheduleElementDto;
import com.neoflex.creditbank.dealservice.enums.CreditStatus;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit")
public class Credit {

    @Id
    @Column(name = "credit_id")
    @UuidGenerator
    private UUID creditId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    @Type(value = JsonBinaryType.class)
    @Column(name = "payment_schedule", columnDefinition = "jsonb")
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    @Enumerated(value = EnumType.STRING)
    private CreditStatus creditStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Credit credit = (Credit) o;
        return creditId != null && Objects.equals(creditId, credit.creditId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}