package com.devsu.hackerearth.backend.account.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.RequestTransactionDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// api/transactions
	// Get all transactions
	@GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
		List<TransactionDto> accounts = transactionService.getAll();
		if (accounts.isEmpty())	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	// api/transactions/{id}
	// Get transactions by id
	@GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
		TransactionDto account = transactionService.getById(id);
		if (account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(account, HttpStatus.OK); 	
	}

	// api/transactions
	// Create transactions
	@PostMapping
	public ResponseEntity<TransactionDto> create(@RequestBody RequestTransactionDto transactionDto) {
		TransactionDto account = transactionService.create(transactionDto);
		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(account, HttpStatus.CREATED); 
	}

	// api/transactions/last-transaction/{accountId}
	// Get last transaction by account id
	@GetMapping("/last-transaction/{accountId}")
	public ResponseEntity<List<TransactionDto>> getLastByAccountId(@PathVariable Long accountId) {
		List<TransactionDto> transaction = transactionService.getLastByAccountId(accountId);
		if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

	// api/transactions/clients/{clientId}/report
	// Get report
	@GetMapping("/clients/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId, 
														 @RequestParam("dateTransactionStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransactionStart, 
														 @RequestParam("dateTransactionEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransactionEnd) {
		System.out.println(dateTransactionStart);
		System.out.println(dateTransactionEnd);
		List<BankStatementDto> reports  = transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
		if (reports.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(reports, HttpStatus.OK); 															
	}

}
