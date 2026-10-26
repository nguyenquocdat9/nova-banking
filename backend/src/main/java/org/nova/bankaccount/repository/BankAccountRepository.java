package org.nova.bankaccount.repository;

import org.nova.bankaccount.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    @Query(value = "SELECT nextval('bank_account_number_seq')", nativeQuery = true)
    Long getNextAccountNumber();
}
