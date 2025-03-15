package com.devsu.hackerearth.backend.account.infrastructure.persistence.entity;

import jakarta.persistence.*;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity extends Base {

    @Column(unique = true, nullable = false, length = 20)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private AccountType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal initialAmount;

    @Column(nullable = false, precision = 15, scale = 2)
	private boolean isActive;

    @Column(name = "client_code", nullable = false)
    private String clientCode;
}
