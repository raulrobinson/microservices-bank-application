package com.devsu.hackerearth.backend.account.infrastructure.controller;

import com.devsu.hackerearth.backend.account.application.dto.AccountDto;
import com.devsu.hackerearth.backend.account.application.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.application.usecases.FindAccountsByClientIdUseCase;
import com.devsu.hackerearth.backend.account.application.usecases.GetAllAccountsUseCase;
import com.devsu.hackerearth.backend.account.domain.model.AccountDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${controller.properties.base-path}/accounts")
@Tag(name = "Accounts", description = "Accounts Operations")
public class AccountQueryController {

    private final GetAllAccountsUseCase getAllAccounts;
    private final FindAccountsByClientIdUseCase accountsByClientId;

    public AccountQueryController(GetAllAccountsUseCase getAllAccounts, FindAccountsByClientIdUseCase accountsByClientId) {
        this.getAllAccounts = getAllAccounts;
        this.accountsByClientId = accountsByClientId;
    }

    /**
     * Get all accounts
     * @return List<AccountDto>
     */
    @GetMapping
    @Operation(summary = "Get all accounts", description = "Get all accounts")
    @ApiResponse(responseCode = "200", description = "List of accounts")
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = AccountMapper
                .INSTANCE.toAccountDtoList(getAllAccounts.handle());
        if (accounts.isEmpty())	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Get accounts by client id
     * @param clientCode String clientCode (required)
     * @return List<AccountDomain>
     */
    @GetMapping("/{clientCode}")
    public ResponseEntity<List<AccountDto>> getAccountsByClientId(@PathVariable String clientCode) {
        List<AccountDto> accounts = AccountMapper
                .INSTANCE.toAccountDtoList(accountsByClientId.handle(clientCode));
        if (accounts.isEmpty())	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
