package com.devsu.hackerearth.backend.account.application.mapper;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.application.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.application.dto.ClientDto;
import com.devsu.hackerearth.backend.account.application.dto.TransactionDto;

import static com.devsu.hackerearth.backend.account.infrastructure.shared.util.Convert.convertToDate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapperImpl implements TransactionMapper{

    public TransactionDto transactionEntityToDto(TransactionEntity transactionEntity) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transactionEntity.getId());
        transactionDto.setAmount(transactionEntity.getAmount());
        transactionDto.setType(String.valueOf(transactionEntity.getType()));
        transactionDto.setAccountId(transactionEntity.getAccountEntity().getId());
        transactionDto.setDate(transactionEntity.getDate());
        transactionDto.setBalance(transactionEntity.getBalance());
        return transactionDto;
    }

    public List<TransactionDto> transactionEntitiesToDtoList(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
            .map(this::transactionEntityToDto)
            .collect(Collectors.toList());
    }

    public BankStatementDto transactionEntityToBankStatementDto(TransactionEntity transactionEntity, ClientDto client) {
        BankStatementDto bankStatementDto = new BankStatementDto();
        bankStatementDto.setAmount(transactionEntity.getAmount());
        bankStatementDto.setDate(convertToDate(transactionEntity.getDate()));
        bankStatementDto.setClient(client);
        bankStatementDto.setAccountNumber(transactionEntity.getAccountEntity().getNumber());
        bankStatementDto.setBalance(transactionEntity.getBalance());
        bankStatementDto.setTransactionType(String.valueOf(transactionEntity.getType()));
        bankStatementDto.setAccountType(String.valueOf(transactionEntity.getAccountEntity().getType()));
        bankStatementDto.setInitialAmount(transactionEntity.getAccountEntity().getInitialAmount());
        bankStatementDto.setBalance(transactionEntity.getBalance());
        return bankStatementDto;
    }

    public List<BankStatementDto> transactionEntitiesToBankStatementDtoList(List<TransactionEntity> transactionEntities, ClientDto client) {
        return transactionEntities.stream()
            .map(transaction -> transactionEntityToBankStatementDto(transaction, client))
            .collect(Collectors.toList());
    }
    
}
