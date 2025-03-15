package com.devsu.hackerearth.backend.account.application.mapper;

import com.devsu.hackerearth.backend.account.application.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.application.dto.ClientDto;
import com.devsu.hackerearth.backend.account.application.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactionMapper {

    TransactionMapper INSTANCE = new TransactionMapperImpl();

    TransactionDto transactionEntityToDto(TransactionEntity transactionEntity);
    List<TransactionDto> transactionEntitiesToDtoList(List<TransactionEntity> transactionEntities);
    BankStatementDto transactionEntityToBankStatementDto(TransactionEntity transactionEntity, ClientDto client);
    List<BankStatementDto> transactionEntitiesToBankStatementDtoList(List<TransactionEntity> transactionEntities, ClientDto client);
}
