package com.devsu.hackerearth.backend.account.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "RequestTransactionDto", description = "Request Transaction DTO")
public class RequestTransactionDto {

	@Schema(description = "Transaction type", example = "DEPOSIT")
    private String type;

	@Schema(description = "Transaction amount", example = "100.0")
	private double amount;

	@Schema(description = "Account id", example = "1")
	private Long accountId;

	@Schema(description = "Client code", example = "CLI-1712345678901")
	private String clientCode;
}
