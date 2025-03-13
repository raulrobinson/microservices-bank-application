package com.devsu.hackerearth.backend.account.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.exception.GlobalException;
import com.devsu.hackerearth.backend.account.exception.NoContentException;
import com.devsu.hackerearth.backend.account.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.RequestTransactionDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.enumeration.TransactionType;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final RestTemplate restTemplate;

    @Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
        this.restTemplate = new RestTemplate();
	}

    // Get all transactions
    @Override
    public List<TransactionDto> getAll() {
        List<TransactionDto> transactions = transactionRepository.findAll().stream()
            .map(transactionMapper::transactionEntityToDto)
            .collect(Collectors.toList());
        if (transactions.isEmpty()) throw new NoContentException("No transactions found");
        transactionLogger.info("Found {} transactions", transactions.size());
        return transactions;
    }

    // Get transactions by id
    @Override
    public TransactionDto getById(Long id) {
        // Cheack if acocunt exists with the given ID
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) throw new GlobalException("Transaction with ID " + id + " not found");
        transactionLogger.info("transaction with ID {}", id);
        return transactionMapper.transactionEntityToDto(transaction);
    }

    // Create transaction
    @Override
    public TransactionDto create(RequestTransactionDto transactionDto) {
        // Cheack if acocunt exists with the given ID
        List<Account> accounts = accountRepository.findAccountByClientId(transactionDto.getAccountId()); 
        if (accounts == null) throw new GlobalException("Account with ID " + transactionDto.getAccountId() + " not found");
        System.out.println("Accounts: " + accounts);

        // Cheack if acocunt is active
        Account account = accountRepository.findAccountByClientIdAndIsActive(transactionDto.getAccountId(), true);

        // Create transaction object and set values
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDto.getType()));
        transaction.setAccount(account); 
        transaction.setDate(LocalDateTime.now());

        // Validate transaction type and set amount
        switch (transactionDto.getType()) {
            case "DEPOSIT":
                transaction.setType(TransactionType.DEPOSIT);
                transaction.setAmount(transactionDto.getAmount());
                break;
            case "WITHDRAWAL":
                transaction.setType(TransactionType.WITHDRAWAL);
                transaction.setAmount(-transactionDto.getAmount()); // Operation is NEGATIV (-$$)
                break;        
            default:
                throw new GlobalException("Transaction type not valid");
        }

        // Obtain balance of last transaction
        Transaction lastTransaction = transactionRepository
            .findByAccountIdOrderByDateDesc(account.getId(), PageRequest.of(0,1))
            .stream().findFirst().orElse(null);

        double lastBalance = (lastTransaction != null) ? lastTransaction.getBalance() : 0;

        // Calculate new balance
        transaction.setBalance(lastBalance + transaction.getAmount());
        if (transaction.getBalance() < 0) {
            throw new GlobalException("Insufficient balance");
        }
        
        // Saving Transaction
        Transaction savedTransaction = transactionRepository.save(transaction);
        if (savedTransaction.getId() != 0) {
            transactionLogger.info("Transaction with ID {} saved for client ID {}",
                    savedTransaction.getId(),
                    savedTransaction.getAccount().getClientId()); 
            return transactionMapper.transactionEntityToDto(savedTransaction);
        }

		throw new GlobalException("Transaction not saved");
    }

    // Report
    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, 
                                                                        LocalDate dateTransactionStart,
                                                                        LocalDate dateTransactionEnd) {
        List<Account> account = accountRepository.findAccountByClientId(clientId);

        List<Transaction> transactions = transactionRepository
            .findByAccountIdInAndDateBetween(account.stream().map(Account::getId).collect(Collectors.toList()),
                                            dateTransactionStart.atStartOfDay(),
                                            dateTransactionEnd.atTime(23, 59, 59));

        if (transactions.isEmpty()) throw new GlobalException("Transactions not found");
        transactionLogger.info("Transactions for client ID {} between {} and {}", clientId, dateTransactionStart, dateTransactionEnd);

        ClientDto client = findClientByClientId(clientId).getBody();
        return transactionMapper.transactionEntitiesToBankStatementDtoList(transactions, client);
    }

    // If you need it
    @Override
    public List<TransactionDto> getLastByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findTransactionByLastByAccountId(accountId);
        if (transactions.isEmpty()) throw new GlobalException("Transactions not found");
        transactionLogger.info("Last transaction for account ID {}", accountId);
        return transactions.stream()
            .map(transactionMapper::transactionEntityToDto)
            .collect(Collectors.toList());
    }

    // ------------- CLIENT SERVICE ----------
    private ResponseEntity<ClientDto> findClientByClientId(Long clientId) {
        String clientUrl = "http://localhost:8001/api/clients/";
        String url = clientUrl + "/" + clientId;
        return restTemplate.getForEntity(url, ClientDto.class);
    }
    
}
