package com.devsu.hackerearth.backend.account.application.mapper;

import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.application.dto.AccountDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper{

    public AccountDto accountEntityToDto(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(accountEntity.getId());
        accountDto.setClientCode(accountEntity.getClientCode());
        accountDto.setType(String.valueOf(accountEntity.getType()));
        accountDto.setNumber(accountEntity.getNumber());
        accountDto.setInitialAmount(accountEntity.getInitialAmount());
        accountDto.setActive(accountEntity.isActive());
        return accountDto;
    }

    @Override
    public AccountDomain accountEntityToDomain(AccountEntity accountEntity) {
        AccountDomain accountDomain = new AccountDomain();
        accountDomain.setId(accountEntity.getId());
        accountDomain.setClientCode(accountEntity.getClientCode());
        accountDomain.setType(String.valueOf(accountEntity.getType()));
        accountDomain.setNumber(accountEntity.getNumber());
        accountDomain.setInitialAmount(accountEntity.getInitialAmount());
        accountDomain.setActive(accountEntity.isActive());
        return accountDomain;
    }

    public AccountDto accountDomainToDto(AccountDomain account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setClientCode(account.getClientCode());
        accountDto.setType(String.valueOf(account.getType()));
        accountDto.setNumber(account.getNumber());
        accountDto.setInitialAmount(account.getInitialAmount());
        accountDto.setActive(account.isActive());
        return accountDto;
    }

    @Override
    public List<AccountDto> toAccountDtoList(List<AccountDomain> accounts) {
        if (accounts == null) {
            return null;
        } else {
            List<AccountDto> list = new ArrayList<>(accounts.size());
            for(AccountDomain clientDomain : accounts) {
                list.add(this.accountDomainToDto(clientDomain));
            }
            return list;
        }
    }

}
