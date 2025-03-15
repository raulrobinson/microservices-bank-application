package com.devsu.hackerearth.backend.account.infrastructure.controller;

import com.devsu.hackerearth.backend.account.application.dto.AccountDto;
import com.devsu.hackerearth.backend.account.application.dto.AccountRequestDto;
import com.devsu.hackerearth.backend.account.application.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.application.usecases.CreateAccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${controller.properties.base-path}/accounts")
@Tag(name = "Account Command", description = "Account Command Operations")
public class AccountCommandController {

    private final CreateAccountUseCase createAccount;

    public AccountCommandController(CreateAccountUseCase createAccount) {
        this.createAccount = createAccount;
    }

	/**
	 * Create account
	 * @param accountDto AccountRequestDto object
	 * @return ResponseEntity<AccountDto>
	 */
	@PostMapping
	@Operation(summary = "Create account", description = "Create account")
	@ApiResponse(responseCode = "201", description = "Account created")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	public ResponseEntity<AccountDto> create(@RequestBody AccountRequestDto accountDto) {
		AccountDto account = AccountMapper
				.INSTANCE.accountDomainToDto(createAccount.execute(accountDto));
		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}



//	// api/transactions
//	// Create transactions
//	@PostMapping
//	public ResponseEntity<TransactionDto> create(@RequestBody RequestTransactionDto transactionDto) {
//		TransactionDto account = createAccount.create(transactionDto);
//		if (account == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(account, HttpStatus.CREATED);
//	}

}
