package com.neoflex.creditbank.dealservice.repositories;

import com.neoflex.creditbank.dealservice.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditRepository extends JpaRepository<Credit, UUID> {
}
