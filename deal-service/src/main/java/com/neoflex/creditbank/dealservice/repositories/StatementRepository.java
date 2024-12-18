package com.neoflex.creditbank.dealservice.repositories;

import com.neoflex.creditbank.dealservice.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatementRepository extends JpaRepository<Statement, UUID> {
}
