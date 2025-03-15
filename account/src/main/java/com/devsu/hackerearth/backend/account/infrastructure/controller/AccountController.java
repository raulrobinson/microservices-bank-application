package com.devsu.hackerearth.backend.account.infrastructure.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.application.dto.AccountDto;
import com.devsu.hackerearth.backend.account.application.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.domain.repository.AccountRepository;

@RestController
@RequestMapping("${controller.properties.base-path}/accounts")
public class AccountController {

//	private final AccountRepository accountRepository;
//
//	public AccountController(AccountRepository accountRepository) {
//		this.accountRepository = accountRepository;
//	}
//


//	// api/accounts/{id}
//	// Get accounts by id
//	@GetMapping("/{id}")
//	public ResponseEntity<List<AccountDto>> get(@PathVariable Long id){
//		List<AccountDto> account = accountRepository.getById(id);
//		if (account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(account, HttpStatus.OK);
//	}

//	// api/accounts/{id}
//	// Update accounts
//	@PutMapping("/{id}")
//	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto){
//		AccountDto account = accountRepository.update(id, accountDto);
//		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(account, HttpStatus.OK);
//	}
//
//	// api/accounts/{id}
//	// Partial update accounts
//	@PatchMapping("/{id}")
//	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id, @RequestBody PartialAccountDto partialAccountDto){
//		AccountDto account = accountRepository.partialUpdate(id, partialAccountDto);
//		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(account, HttpStatus.OK);
//	}
//
//	// api/accounts/{id}
//	// Delete accounts
//	@DeleteMapping("/{id}")
//	public void delete(@PathVariable Long id){
//		accountRepository.deleteById(id);
//	}
	
}

