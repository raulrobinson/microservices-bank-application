package com.devsu.hackerearth.backend.account.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdInAndDateBetween(Collection<Long> account_id, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account.id = ?1")
    List<Transaction> findTransactionByAccountId(Long accountId);

    List<Transaction> findByAccountIdOrderByDateDesc(Long accountId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.account.id = ?1 ORDER BY t.date DESC")
    List<Transaction> findTransactionByLastByAccountId(Long accountId);
    
}
