package com.devsu.hackerearth.backend.account.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

import static com.devsu.hackerearth.backend.account.util.Convert.convertToDate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionDto transactionEntityToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setType(String.valueOf(transaction.getType()));
        transactionDto.setAccountId(transaction.getAccount().getId());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setBalance(transaction.getBalance());

        return transactionDto;
    }

    public List<TransactionDto> transactionEntitiesToDtoList(List<Transaction> transactions) {
        return transactions.stream()
            .map(this::transactionEntityToDto)
            .collect(Collectors.toList());
    }

    public BankStatementDto transactionEntityToBankStatementDto(Transaction transaction, ClientDto client) {
        BankStatementDto bankStatementDto = new BankStatementDto();
        bankStatementDto.setAmount(transaction.getAmount());
        bankStatementDto.setDate(convertToDate(transaction.getDate()));
        bankStatementDto.setClient(client);
        bankStatementDto.setAccountNumber(transaction.getAccount().getNumber());
        bankStatementDto.setBalance(transaction.getBalance());
        bankStatementDto.setTransactionType(String.valueOf(transaction.getType()));
        bankStatementDto.setAccountType(String.valueOf(transaction.getAccount().getType()));
        bankStatementDto.setInitialAmount(transaction.getAccount().getInitialAmount());
        bankStatementDto.setBalance(transaction.getBalance());

        return bankStatementDto;
    }

    public List<BankStatementDto> transactionEntitiesToBankStatementDtoList(List<Transaction> transactions, ClientDto client) {
        return transactions.stream()
            .map(transaction -> transactionEntityToBankStatementDto(transaction, client))
            .collect(Collectors.toList());
    }
    
}
