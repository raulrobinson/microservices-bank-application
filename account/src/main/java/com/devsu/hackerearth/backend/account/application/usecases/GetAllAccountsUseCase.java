package com.devsu.hackerearth.backend.account.application.usecases;

import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import com.devsu.hackerearth.backend.account.domain.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.infrastructure.shared.exception.NoContentException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAccountsUseCase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GetAllAccountsUseCase.class);

    private final AccountRepository accountRepository;

    public GetAllAccountsUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDomain> handle() {
        List<AccountDomain> accounts = accountRepository.getAllAccounts();
        if (accounts.isEmpty()) throw new NoContentException("No accounts found");
        log.info("Found {} accounts", accounts.size());
        return accounts;
    }
}
