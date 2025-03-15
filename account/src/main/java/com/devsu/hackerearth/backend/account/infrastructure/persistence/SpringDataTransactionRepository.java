package com.devsu.hackerearth.backend.account.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findTransactionEntitiesByAccountEntity_ClientCodeOrderByDateDesc(String accountEntityClientCode);

    //@Query("SELECT t FROM TransactionEntity t WHERE t.account.id = ?1")
    //List<TransactionEntity> findTransactionByAccountId(Long accountId);
    //List<TransactionEntity> findTransactionEntityByAccountEntity_Id(Long accountEntityId);

    //List<TransactionEntity> findByAccountIdOrderByDateDesc(Long accountId, Pageable pageable);
    //List<TransactionEntity> findTransactionEntityByAccountEntity_IdAndDate(Long accountEntityId, Pageable pageable);

    //@Query("SELECT t FROM TransactionEntity t WHERE t.account.id = ?1 ORDER BY t.date DESC")
    //List<TransactionEntity> findTransactionByLastByAccountId(Long accountId);
    //List<TransactionEntity> findTransactionByAccountIdOrderByDateDesc(Long accountId);


}
