package com.devsu.hackerearth.backend.account.application.mapper;

import com.devsu.hackerearth.backend.account.application.dto.AccountDto;
import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountMapper {

    AccountMapper INSTANCE = new AccountMapperImpl();

    AccountDomain accountEntityToDomain(AccountEntity accountEntity);
    List<AccountDto> toAccountDtoList(List<AccountDomain> accounts);
    AccountDto accountDomainToDto(AccountDomain execute);
}
