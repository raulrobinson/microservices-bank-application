package com.devsu.hackerearth.backend.account.domain.repository;

import java.time.LocalDate;
import java.util.List;

import com.devsu.hackerearth.backend.account.application.dto.ClientDto;
import org.springframework.data.repository.query.Param;

import com.devsu.hackerearth.backend.account.application.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.application.dto.RequestTransactionDto;
import com.devsu.hackerearth.backend.account.application.dto.TransactionDto;

public interface TransactionRepository {

    List<TransactionDto> getAll();
//	TransactionDto getById(Long id);
	TransactionDto create(RequestTransactionDto transactionDto);
//    List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId,
//                                                                        @Param("dateTransactionStart") LocalDate dateTransactionStart,
//                                                                        @Param("dateTransactionEnd") LocalDate dateTransactionEnd);
//    List<TransactionDto> getLastByAccountId(Long accountId);

//    ClientDto getClientById(Long clientId);
//
//    List<ClientDto> getAllClients();
}
