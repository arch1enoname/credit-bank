package com.neoflex.creditbank.dealservice.entities;

import com.neoflex.creditbank.dealservice.dtos.EmploymentDto;
import com.neoflex.creditbank.dealservice.dtos.PassportDto;
import com.neoflex.creditbank.dealservice.enums.Gender;
import com.neoflex.creditbank.dealservice.enums.MaritalStatus;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "client_id")
    @UuidGenerator
    private UUID clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(value = EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Type(value = JsonBinaryType.class)
    @Column(name = "passport", columnDefinition = "jsonb")
    private PassportDto passport;

    @Type(value = JsonBinaryType.class)
    @Column(name = "employment", columnDefinition = "jsonb")
    private EmploymentDto employment;

    @Column(name = "account_number")
    private String accountNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return clientId != null && Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
