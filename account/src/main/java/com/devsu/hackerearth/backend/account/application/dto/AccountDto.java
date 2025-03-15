package com.devsu.hackerearth.backend.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String number;
	private String type;
	private BigDecimal initialAmount;
	private boolean isActive;
	private String clientCode;
}
