package com.devsu.hackerearth.backend.account.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@Component
public class AccountMapper {

    public AccountDto accountEntityToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setClientId(account.getClientId());
        accountDto.setType(String.valueOf(account.getType()));
        accountDto.setNumber(account.getNumber());
        accountDto.setInitialAmount(account.getInitialAmount());
        accountDto.setActive(account.isActive());

        return accountDto;
    }
    
}
