package com.devsu.hackerearth.backend.account.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AccountRequestDto", description = "Account request data transfer object")
public class AccountRequestDto {

    @Schema(description = "Account type", example = "SAVINGS")
    private String type;

    @Schema(description = "Account initial amount", example = "1000.0")
    private BigDecimal initialAmount;

    @Schema(description = "Account is active", example = "true")
    private boolean isActive;

    @Schema(description = "Client code", example = "CLI-1712345678901")
    private String clientCode;
}
