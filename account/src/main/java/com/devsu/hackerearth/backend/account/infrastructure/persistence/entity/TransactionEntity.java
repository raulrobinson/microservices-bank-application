package com.devsu.hackerearth.backend.account.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.TransactionType;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionEntity extends Base {

	private LocalDateTime date;
	private TransactionType type;
	private BigDecimal amount;
	private BigDecimal balance;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity accountEntity;
}
