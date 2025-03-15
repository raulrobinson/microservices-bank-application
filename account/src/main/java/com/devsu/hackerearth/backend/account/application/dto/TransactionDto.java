package com.devsu.hackerearth.backend.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long id;
    private LocalDateTime date;
	private String type;
	private BigDecimal amount;
	private BigDecimal balance;
	private Long accountId;
}
