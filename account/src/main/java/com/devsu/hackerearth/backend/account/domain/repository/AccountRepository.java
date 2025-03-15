package com.devsu.hackerearth.backend.account.domain.repository;

import java.util.List;

import com.devsu.hackerearth.backend.account.application.dto.AccountRequestDto;
import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;

public interface AccountRepository {

    List<AccountDomain> getAllAccounts();
	List<AccountDomain> getAccountsByClientCode(String clientCode);
    AccountDomain createAccount(AccountRequestDto accountDto);
//	AccountDto update(Long id, AccountDto accountDto);
//	AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto);
//	void deleteById(Long id);
}
