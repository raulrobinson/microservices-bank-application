package com.devsu.hackerearth.backend.account.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.exception.GlobalException;
import com.devsu.hackerearth.backend.account.exception.NoContentException;
import com.devsu.hackerearth.backend.account.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.enumeration.AccountType;
import com.devsu.hackerearth.backend.account.model.enumeration.TransactionType;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.restTemplate = new RestTemplate();
    }

    // Get all accounts - OK
    @Override
    public List<AccountDto> getAll() {
        // Obtain All Accounts
        List<AccountDto> accounts = accountRepository.findAll().stream()
                .map(accountMapper::accountEntityToDto)
                .collect(Collectors.toList());
        if (accounts.isEmpty())
            throw new NoContentException("No accounts found");

        transactionLogger.info("Found {} accounts", accounts.size());
        return accounts;
    }

    // Get accounts by id - OK
    @Override
    public List<AccountDto> getById(Long id) {
        // Search Account by ID if exists registered
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null)
            throw new GlobalException("Account with ID " + id + " not found");

        transactionLogger.info("Account with ID {}", id);
        return accountRepository.findAccountByClientId(id).stream()
                .map(accountMapper::accountEntityToDto)
                .collect(Collectors.toList());
    }

    // Create account
    @Override
    public AccountDto create(AccountDto accountDto) {
        // Check if exists client with a account type and same number
        Long accountNumberToCheck = accountDto.getClientId();
        AccountType accountTypeToCheck = AccountType.valueOf(accountDto.getType());
        String accountNumber = accountDto.getNumber();
        if (accountRepository.existsByNumberAndTypeAndClientId(accountNumber, accountTypeToCheck,
                accountNumberToCheck)) {
            throw new GlobalException("Ya existe una cuenta con el mismo número y tipo para este cliente.");
        }

        // Check if client exists with the given ID in CLIENT service
        ResponseEntity<ClientDto> client = findClientByClientId(accountDto.getClientId());

        // Check if client exists with the given ID and get the ID of the client if
        // exists
        if (client.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(client.getBody()).getId() != null) {
            transactionLogger.info("Client with ID {} found", client.getBody().getId());

            Account newAccount = new Account();
            newAccount.setId(accountDto.getId());
            newAccount.setClientId(client.getBody().getId());
            newAccount.setType(AccountType.valueOf(accountDto.getType()));
            newAccount.setNumber(accountDto.getNumber());
            newAccount.setInitialAmount(accountDto.getInitialAmount());
            newAccount.setActive(accountDto.isActive());

            Account savedAccount = accountRepository.save(newAccount);
            if (savedAccount.getClientId() != 0) {
                transactionLogger.info("Client with ID {} saved", savedAccount.getClientId());

                // Create transaction
                Transaction transaction = new Transaction();
                transaction.setAmount(accountDto.getInitialAmount());
                transaction.setType(TransactionType.DEPOSIT);
                transaction.setAccount(savedAccount);
                transaction.setDate(LocalDateTime.now());

                // Get last balance
                List<Transaction> accountBalance = transactionRepository.findTransactionByAccountId(accountDto.getId());
                double lastBalance = accountBalance.isEmpty() ? 0
                        : accountBalance.get(accountBalance.size() - 1).getBalance();
                transaction.setBalance(accountDto.getInitialAmount() + lastBalance);

                Transaction savedTransaction = transactionRepository.save(transaction);
                if (savedTransaction.getId() != 0) {
                    transactionLogger.info("Transaction with ID {} saved for client ID {}", savedTransaction.getId(),
                            savedTransaction.getAccount().getClientId());
                    return accountMapper.accountEntityToDto(savedAccount);
                }
            }

        } else {
            throw new GlobalException("Client not found with ID: " + accountDto.getClientId());
        }

        throw new GlobalException("Transaction not saved");
    }

    // Update account
    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        // Find Account if exists for ID and Client ID
        Optional<Account> accountById = accountRepository.findById(id);
        List<Account> accountByClientId = accountRepository.findAccountByClientId(accountDto.getClientId());

        if (accountById.isEmpty()) {
            throw new GlobalException("Account with ID " + id + " not found");
        } else if (accountByClientId == null) {
            throw new GlobalException("Account with Client ID: " + accountDto.getClientId() + " not found");
        }

        // Check if client exists with the given ID in CLIENT service
        ResponseEntity<ClientDto> client = findClientByClientId(accountDto.getClientId());

        if (client.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(client.getBody()).getId() != null) {
            transactionLogger.info("Client with ID {} found", client.getBody().getId());

            accountById.get().setClientId(accountDto.getClientId());
            accountById.get().setType(AccountType.valueOf(accountDto.getType()));
            accountById.get().setNumber(accountDto.getNumber());
            accountById.get().setInitialAmount(accountDto.getInitialAmount());
            accountById.get().setActive(accountDto.isActive());

            Account updatedEntity = accountRepository.save(accountById.get());
            if (Objects.equals(updatedEntity.getId(), id)) {
                transactionLogger.info("Account with ID {} updated", updatedEntity.getId());
                return accountMapper.accountEntityToDto(updatedEntity);
            }
        } else {
            throw new GlobalException("Client not found with ID: " + accountDto.getClientId());
        }

        throw new GlobalException("Account with ID " + id + " not updated");
    }

    // Partial update account
    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Find Account if exists for ID and is Active
        Optional<Account> accountById = accountRepository.findById(id);
        Account accountByClientIdAndIsActive = accountRepository.findAccountByClientIdAndIsActive(id, partialAccountDto.isActive());
        if (accountById.isEmpty()) {
            throw new GlobalException("Account with ID " + id + " not found");
        } else if (accountByClientIdAndIsActive != null) {
            throw new GlobalException("Account already exists with Active: " + partialAccountDto.isActive());
        }

        // Check if client exists with the given ID in CLIENT service
        ResponseEntity<ClientDto> client = findClientByClientId(accountById.get().getClientId());
        
        if (client.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(client.getBody()).getId() != null) {
            transactionLogger.info("Client with ID {} found", client.getBody().getId());

            accountById.get().setActive(partialAccountDto.isActive());

            Account updatedEntity = accountRepository.save(accountById.get());
            if (Objects.equals(updatedEntity.getId(), id)) {
                transactionLogger.info("Account with ID {} updated", updatedEntity.getId());
                return accountMapper.accountEntityToDto(updatedEntity);
            }
            
        } else {
            throw new GlobalException("Client not found with ID: " + accountById.get().getClientId());
        }

        throw new GlobalException("Account with ID " + id + " not updated");
    }

    // Delete account
    @Override
    public void deleteById(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            transactionLogger.info("Account with ID {} deleted", id);
        } else {
            throw new GlobalException("Account with ID " + id + " not found");
        }
    }

    private ResponseEntity<ClientDto> findClientByClientId(Long clientId) {
        String clientUrl = "http://localhost:8001/api/clients/";
        String url = clientUrl + "/" + clientId;
        return restTemplate.getForEntity(url, ClientDto.class);
    }

}
