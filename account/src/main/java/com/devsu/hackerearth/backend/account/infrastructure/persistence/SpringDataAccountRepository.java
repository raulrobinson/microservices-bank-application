package com.devsu.hackerearth.backend.account.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.AccountType;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a WHERE a.clientCode = ?1")
    List<AccountEntity> findAccountsByClientCode(String clientCode);

    @Query("SELECT a FROM AccountEntity a WHERE a.clientCode = ?1 AND a.isActive = ?2")
    AccountEntity findAccountByClientIdAndIsActive(Long clientId, boolean active);

//    boolean existsByNumberAndTypeAndClientId(String number, AccountType type, Long clientId);
//
//    @Query("SELECT MAX(b.id) FROM AccountEntity b")
//    Optional<Long> findMaxId();

//    @Query("SELECT COUNT(a) FROM AccountEntity a WHERE a.clientId = :clientId AND a.type = :accountType")
//    long countByClientIdAndAccountType(@Param("clientId") Long clientId, @Param("accountType") AccountType accountType);


}
