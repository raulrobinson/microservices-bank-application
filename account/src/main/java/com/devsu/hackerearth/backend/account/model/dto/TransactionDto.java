package com.devsu.hackerearth.backend.account.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long id;
    private LocalDateTime date;
	private String type;
	private double amount;
	private double balance;
	private Long accountId;
}
