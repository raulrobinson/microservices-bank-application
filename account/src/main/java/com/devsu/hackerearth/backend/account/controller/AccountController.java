package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

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

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// api/accounts
	// Get all accounts
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll(){
		List<AccountDto> accounts = accountService.getAll();
		if (accounts.isEmpty())	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	// api/accounts/{id}
	// Get accounts by id
	@GetMapping("/{id}")
	public ResponseEntity<List<AccountDto>> get(@PathVariable Long id){
		List<AccountDto> account = accountService.getById(id);
		if (account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(account, HttpStatus.OK); 		
	}

	// api/accounts
	// Create accounts
	@PostMapping
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		AccountDto account = accountService.create(accountDto);
		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(account, HttpStatus.CREATED); 
	}

	// api/accounts/{id}
	// Update accounts
	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto){
		AccountDto account = accountService.update(id, accountDto);
		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	// api/accounts/{id}
	// Partial update accounts
	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id, @RequestBody PartialAccountDto partialAccountDto){
		AccountDto account = accountService.partialUpdate(id, partialAccountDto);
		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	// api/accounts/{id}
	// Delete accounts
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){		
		accountService.deleteById(id);
	}
	
}

