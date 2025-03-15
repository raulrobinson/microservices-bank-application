package com.devsu.hackerearth.backend.account.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.domain.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.infrastructure.client.ClientFeignClient;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.TransactionEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.GlobalException;
import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.NoContentException;
import com.devsu.hackerearth.backend.account.application.mapper.TransactionMapperImpl;
import com.devsu.hackerearth.backend.account.application.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.application.dto.ClientDto;
import com.devsu.hackerearth.backend.account.application.dto.RequestTransactionDto;
import com.devsu.hackerearth.backend.account.application.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.TransactionType;

@Service
public class JpaTransactionRepository implements TransactionRepository {

    //private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");
    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger(JpaTransactionRepository.class);

	private final SpringDataAccountRepository springDataAccountRepository;
	private final SpringDataTransactionRepository springDataTransactionRepository;
    private final TransactionMapperImpl transactionMapperImpl;

    private final ClientFeignClient client;

    @Autowired
	public JpaTransactionRepository(SpringDataTransactionRepository springDataTransactionRepository, SpringDataAccountRepository springDataAccountRepository, TransactionMapperImpl transactionMapperImpl, ClientFeignClient client) {
		this.springDataTransactionRepository = springDataTransactionRepository;
        this.springDataAccountRepository = springDataAccountRepository;
        this.transactionMapperImpl = transactionMapperImpl;
        this.client = client;
	}

    // Get all transactions
    @Override
    public List<TransactionDto> getAll() {
        List<TransactionDto> transactions = springDataTransactionRepository
                .findAll()
                .stream()
                .map(transactionMapperImpl::transactionEntityToDto)
                .collect(Collectors.toList());
        if (transactions.isEmpty()) throw new NoContentException("No transactions found");
        transactionLogger.info("Found {} transactions", transactions.size());
        return transactions;
    }

    // Get transactions by id
//    @Override
//    public TransactionDto getById(Long id) {
//        // Cheack if acocunt exists with the given ID
//        TransactionEntity transactionEntity = springDataTransactionRepository.findById(id).orElse(null);
//        if (transactionEntity == null) throw new GlobalException("Transaction with ID " + id + " not found");
//        transactionLogger.info("transaction with ID {}", id);
//        return transactionMapperImpl.transactionEntityToDto(transactionEntity);
//    }

    // Create transaction
    @Override
    public TransactionDto create(RequestTransactionDto transactionDto) {
        // Cheack if acocunt exists with the given ID
        List<AccountEntity> accountEntities = springDataAccountRepository
                .findAccountsByClientCode(transactionDto.getClientCode());
        if (accountEntities == null) throw new GlobalException("Account with ID " + transactionDto.getAccountId() + " not found");
        System.out.println("Accounts: " + accountEntities);

        // Cheack if acocunt is active
        AccountEntity accountEntity = springDataAccountRepository
                .findAccountByClientIdAndIsActive(transactionDto.getAccountId(), true);

        // Create transaction object and set values
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(BigDecimal.valueOf(transactionDto.getAmount()));
        transactionEntity.setType(TransactionType.valueOf(transactionDto.getType()));
        transactionEntity.setAccountEntity(accountEntity);
        transactionEntity.setDate(LocalDateTime.now());

        // Validate transaction type and set amount
        switch (transactionDto.getType()) {
            case "DEPOSIT":
                transactionEntity.setType(TransactionType.DEPOSIT);
                transactionEntity.setAmount(BigDecimal.valueOf(transactionDto.getAmount()));
                break;
            case "WITHDRAWAL":
                transactionEntity.setType(TransactionType.WITHDRAWAL);
                transactionEntity.setAmount(BigDecimal.valueOf(-transactionDto.getAmount())); // Operation is NEGATIV (-$$)
                break;        
            default:
                throw new GlobalException("Transaction type not valid");
        }

        // Obtain balance of last transaction
        TransactionEntity lastTransactionEntity = springDataTransactionRepository
            //.findByAccountIdOrderByDateDesc(accountEntity.getId(), PageRequest.of(0,1))
                // .findTransactionEntityByAccountEntity_Id(accountEntity.getId())
                .findTransactionEntitiesByAccountEntity_ClientCodeOrderByDateDesc(accountEntity.getClientCode())
            .stream().findFirst().orElse(null);

        BigDecimal lastBalance = (lastTransactionEntity != null) ? lastTransactionEntity.getBalance() : BigDecimal.ZERO;

        // Calculate new balance
        transactionEntity.setBalance(lastBalance.add(transactionEntity.getAmount()));
        if (transactionEntity.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new GlobalException("Insufficient balance");
        }
        
        // Saving Transaction
        TransactionEntity savedTransactionEntity = springDataTransactionRepository
                .save(transactionEntity);
        if (savedTransactionEntity.getId() != 0) {
            transactionLogger.info("Transaction with ID {} saved for CLIENT CODE: {}",
                    savedTransactionEntity.getId(),
                    savedTransactionEntity.getAccountEntity().getClientCode());
            return transactionMapperImpl
                    .transactionEntityToDto(savedTransactionEntity);
        }

		throw new GlobalException("Transaction not saved");
    }

    // Report
//    @Override
//    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId,
//                                                                        LocalDate dateTransactionStart,
//                                                                        LocalDate dateTransactionEnd) {
//        List<AccountEntity> accountEntity = springDataAccountRepository.findAccountByClientId(clientId);
//
//        List<TransactionEntity> transactionEntities = springDataTransactionRepository
//            .findByAccountIdInAndDateBetween(accountEntity.stream().map(AccountEntity::getId).collect(Collectors.toList()),
//                                            dateTransactionStart.atStartOfDay(),
//                                            dateTransactionEnd.atTime(23, 59, 59));
//
//        if (transactionEntities.isEmpty()) throw new GlobalException("Transactions not found");
//        transactionLogger.info("Transactions for client ID {} between {} and {}", clientId, dateTransactionStart, dateTransactionEnd);
//
//        ClientDto client = findClientByClientId(clientId).getBody();
//        return transactionMapperImpl.transactionEntitiesToBankStatementDtoList(transactionEntities, client);
//    }

    // If you need it
//    @Override
//    public List<TransactionDto> getLastByAccountId(Long accountId) {
//        List<TransactionEntity> transactionEntities = springDataTransactionRepository.findTransactionByLastByAccountId(accountId);
//        if (transactionEntities.isEmpty()) throw new GlobalException("Transactions not found");
//        transactionLogger.info("Last transaction for account ID {}", accountId);
//        return transactionEntities.stream()
//            .map(transactionMapperImpl::transactionEntityToDto)
//            .collect(Collectors.toList());
//    }

//    // ------------- CLIENT SERVICE ----------
//    private ResponseEntity<ClientDto> findClientByClientId(Long clientId) {
//        String clientUrl = "http://localhost:8001/api/clients/";
//        String url = clientUrl + "/" + clientId;
//        return restTemplate.getForEntity(url, ClientDto.class);
//    }

//    @Override
//    public ClientDto getClientById(Long clientId) {
//        return client.getClientById(clientId);
//    }
//
//    @Override
//    public List<ClientDto> getAllClients() {
//        List<ClientDto> clients = client.getAllClients();
//        if (clients == null) throw new NoContentException("No clients found");
//        return clients;
//    }
    
}
