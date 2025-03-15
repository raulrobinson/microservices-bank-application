package com.devsu.hackerearth.backend.account.application.usecases;

import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import com.devsu.hackerearth.backend.account.domain.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAccountsByClientIdUseCase {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger(FindAccountsByClientIdUseCase.class);

    private final AccountRepository accountRepository;

    public FindAccountsByClientIdUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDomain> handle(String clientId) {
        List<AccountDomain> accounts = accountRepository.getAccountsByClientCode(clientId);
        if (accounts.isEmpty()) throw new NotFoundException("Accounts with client ID " + clientId + " not found");
        transactionLogger.info("Account {} with client ID {} found", accounts.size(), clientId);
        return accounts;
    }

}
