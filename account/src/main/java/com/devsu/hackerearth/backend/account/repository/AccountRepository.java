package com.devsu.hackerearth.backend.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.enumeration.AccountType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountByClientId(Long clientId);

    @Query("SELECT a FROM Account a WHERE a.clientId = ?1 AND a.isActive = ?2")
    Account findAccountByClientIdAndIsActive(Long clientId, boolean active);

    boolean existsByNumberAndTypeAndClientId(String number, AccountType type, Long clientId);
    
}
