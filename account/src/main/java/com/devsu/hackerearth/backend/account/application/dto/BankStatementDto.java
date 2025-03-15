package com.devsu.hackerearth.backend.account.application.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankStatementDto {
    
	private Date date;
	private ClientDto client;
	private String accountNumber;
	private String accountType;
	private BigDecimal initialAmount;
    private boolean isActive;
	private String transactionType;
	private BigDecimal amount;
	private BigDecimal balance;
}
