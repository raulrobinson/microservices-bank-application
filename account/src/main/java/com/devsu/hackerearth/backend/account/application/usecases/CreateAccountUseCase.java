package com.devsu.hackerearth.backend.account.application.usecases;

import com.devsu.hackerearth.backend.account.application.dto.AccountRequestDto;
import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import com.devsu.hackerearth.backend.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDomain execute(AccountRequestDto account) {
        return accountRepository.createAccount(account);
    }

}
