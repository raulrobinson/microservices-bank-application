package com.devsu.hackerearth.backend.account.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.application.dto.AccountRequestDto;
import com.devsu.hackerearth.backend.account.application.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.domain.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.infrastructure.client.ClientFeignClient;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.TransactionEntity;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.TransactionType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.GlobalException;
import com.devsu.hackerearth.backend.account.application.mapper.AccountMapperImpl;
import com.devsu.hackerearth.backend.account.application.dto.ClientDto;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.AccountType;
import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;

@Service
public class JpaAccountRepository implements AccountRepository {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");

    private final Random random = new Random();

    private final SpringDataAccountRepository springDataAccountRepository;
    private final SpringDataTransactionRepository springDataTransactionRepository;
    private final AccountMapperImpl accountMapperImpl;
    private final AccountMapper accountMapper;
    private final ClientFeignClient client;

    @Autowired
    public JpaAccountRepository(SpringDataAccountRepository springDataAccountRepository, AccountMapperImpl accountMapperImpl,
                                SpringDataTransactionRepository springDataTransactionRepository, AccountMapper accountMapper, ClientFeignClient client) {
        this.springDataAccountRepository = springDataAccountRepository;
        this.accountMapperImpl = accountMapperImpl;
        this.springDataTransactionRepository = springDataTransactionRepository;
        this.client = client;
        this.accountMapper = accountMapper;
    }

    // Get all accounts - OK
    @Override
    public List<AccountDomain> getAllAccounts() {
        return springDataAccountRepository.findAll().stream()
                .map(accountMapper::accountEntityToDomain)
                .collect(Collectors.toList());
    }

    // Get accounts by id - OK
    @Override
    public List<AccountDomain> getAccountsByClientCode(String clientCode) {
        return springDataAccountRepository.findAccountsByClientCode(clientCode).stream()
                .map(accountMapper::accountEntityToDomain)
                .collect(Collectors.toList());
    }

    // Create account
    @Override
    public AccountDomain createAccount(AccountRequestDto accountDto) {
        // Check if client exists with the given ID in CLIENT service
        ClientDto findClient = client.getClientByClientCode(accountDto.getClientCode());

        if (findClient == null) {
            throw new GlobalException("Cliente no encontrado con ID: " + accountDto.getClientCode());
        } else {
            // Create account
            AccountEntity newAccountEntity = new AccountEntity();
            newAccountEntity.setClientCode(findClient.getClientCode());
            newAccountEntity.setType(AccountType.valueOf(accountDto.getType()));
            newAccountEntity.setNumber(generateUniqueAccountNumber(AccountType.valueOf(accountDto.getType())));
            newAccountEntity.setInitialAmount(accountDto.getInitialAmount());
            newAccountEntity.setActive(accountDto.isActive());

            // Save account
            AccountEntity savedAccountEntity = springDataAccountRepository.save(newAccountEntity);
            if (savedAccountEntity.getClientCode() != null) {

                // Create transaction
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setAmount(accountDto.getInitialAmount());
                transactionEntity.setType(TransactionType.DEPOSIT);
                transactionEntity.setAccountEntity(savedAccountEntity);
                transactionEntity.setDate(LocalDateTime.now());

                // Get last balance
                List<TransactionEntity> accountBalance = springDataTransactionRepository.findTransactionEntitiesByAccountEntity_ClientCodeOrderByDateDesc(accountDto.getClientCode());
                BigDecimal lastBalance = accountBalance.isEmpty() ? BigDecimal.ZERO
                        : new BigDecimal(accountBalance.get(accountBalance.size() - 1).getBalance().toString());
                transactionEntity.setBalance(accountDto.getInitialAmount().add(lastBalance));

                // Save transaction
                TransactionEntity savedTransactionEntity = springDataTransactionRepository.save(transactionEntity);
                if (savedTransactionEntity.getId() != 0) {
                    transactionLogger.info("Transaction with ID {} saved for client ID {}", savedTransactionEntity.getId(),
                            savedTransactionEntity.getAccountEntity().getClientCode());
                    return accountMapperImpl.accountEntityToDomain(savedAccountEntity);
                }
            }
        }

        throw new GlobalException("Transaction not saved");
    }

    private String generateUniqueAccountNumber(AccountType accountType) {
        String prefix = switch (accountType) {
            case CURRENT -> "CTA";  // Cuenta corriente
            case SAVINGS -> "AH";   // Ahorro
            case CDT -> "CDT";      // Certificado de Depósito a Término
            case CREDIT -> "CR";    // Crédito
        };

        int segment1 = random.nextInt(10000);       // 0000 - 9999
        int segment2 = 900 + random.nextInt(100);   // 0900 - 0999
        int segment3 = random.nextInt(100);         // 00 - 99

        return String.format("%s-%04d-%04d-%02d", prefix, segment1, segment2, segment3);
    }

//    // Update account
//    @Override
//    public AccountDto update(Long id, AccountDto accountDto) {
//        // Find Account if exists for ID and Client ID
//        Optional<Account> accountById = springDataAccountRepository.findById(id);
//        List<Account> accountByClientId = springDataAccountRepository.findAccountByClientId(accountDto.getClientId());
//
//        if (accountById.isEmpty()) {
//            throw new GlobalException("Account with ID " + id + " not found");
//        } else if (accountByClientId == null) {
//            throw new GlobalException("Account with Client ID: " + accountDto.getClientId() + " not found");
//        }
//
//        // Check if client exists with the given ID in CLIENT service
//        ResponseEntity<ClientDto> client = findClientByClientId(accountDto.getClientId());
//
//        if (client.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(client.getBody()).getId() != null) {
//            transactionLogger.info("Client with ID {} found", client.getBody().getId());
//
//            accountById.get().setClientId(accountDto.getClientId());
//            accountById.get().setType(AccountType.valueOf(accountDto.getType()));
//            accountById.get().setNumber(accountDto.getNumber());
//            accountById.get().setInitialAmount(accountDto.getInitialAmount());
//            accountById.get().setActive(accountDto.isActive());
//
//            Account updatedEntity = springDataAccountRepository.save(accountById.get());
//            if (Objects.equals(updatedEntity.getId(), id)) {
//                transactionLogger.info("Account with ID {} updated", updatedEntity.getId());
//                return accountMapperImpl.accountEntityToDto(updatedEntity);
//            }
//        } else {
//            throw new GlobalException("Client not found with ID: " + accountDto.getClientId());
//        }
//
//        throw new GlobalException("Account with ID " + id + " not updated");
//    }
//
//    // Partial update account
//    @Override
//    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
//        // Find Account if exists for ID and is Active
//        Optional<Account> accountById = springDataAccountRepository.findById(id);
//        Account accountByClientIdAndIsActive = springDataAccountRepository.findAccountByClientIdAndIsActive(id, partialAccountDto.isActive());
//        if (accountById.isEmpty()) {
//            throw new GlobalException("Account with ID " + id + " not found");
//        } else if (accountByClientIdAndIsActive != null) {
//            throw new GlobalException("Account already exists with Active: " + partialAccountDto.isActive());
//        }
//
//        // Check if client exists with the given ID in CLIENT service
//        ResponseEntity<ClientDto> client = findClientByClientId(accountById.get().getClientId());
//
//        if (client.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(client.getBody()).getId() != null) {
//            transactionLogger.info("Client with ID {} found", client.getBody().getId());
//
//            accountById.get().setActive(partialAccountDto.isActive());
//
//            Account updatedEntity = springDataAccountRepository.save(accountById.get());
//            if (Objects.equals(updatedEntity.getId(), id)) {
//                transactionLogger.info("Account with ID {} updated", updatedEntity.getId());
//                return accountMapperImpl.accountEntityToDto(updatedEntity);
//            }
//
//        } else {
//            throw new GlobalException("Client not found with ID: " + accountById.get().getClientId());
//        }
//
//        throw new GlobalException("Account with ID " + id + " not updated");
//    }
//
//    // Delete account
//    @Override
//    public void deleteById(Long id) {
//        if (springDataAccountRepository.existsById(id)) {
//            springDataAccountRepository.deleteById(id);
//            transactionLogger.info("Account with ID {} deleted", id);
//        } else {
//            throw new GlobalException("Account with ID " + id + " not found");
//        }
//    }
//
//    private ResponseEntity<ClientDto> findClientByClientId(Long clientId) {
//        String clientUrl = "http://localhost:8001/api/clients/";
//        String url = clientUrl + "/" + clientId;
//        return restTemplate.getForEntity(url, ClientDto.class);
//    }

}
