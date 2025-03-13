package com.devsu.hackerearth.backend.account.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.enumeration.AccountType;
import com.devsu.hackerearth.backend.account.service.AccountService;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountService accountService;

    public DataLoader(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Account Client 1
        /*AccountDto account1 = new AccountDto();
        account1.setClientId(1L);
        account1.setType(AccountType.SAVINGS.name());
        account1.setNumber("111");
        account1.setInitialAmount(1000.0);
        account1.setActive(true);
        accountService.create(account1);
        System.out.println("Account loaded Client ID: " + account1.getClientId());

        // Account Client 2
        AccountDto account2 = new AccountDto();
        account2.setClientId(2L);
        account2.setType(AccountType.SAVINGS.name());
        account2.setNumber("222");
        account2.setInitialAmount(2000.0);
        account2.setActive(true);
        accountService.create(account2);
        System.out.println("Account loaded Client ID: " + account2.getClientId());

        // Account Client 3
        AccountDto account3 = new AccountDto();
        account3.setClientId(3L);
        account3.setType(AccountType.SAVINGS.name());
        account3.setNumber("333");
        account3.setInitialAmount(3000.0);
        account3.setActive(false);
        accountService.create(account3);
        System.out.println("Account loaded Client ID: " + account3.getClientId());*/

    }
    
}
