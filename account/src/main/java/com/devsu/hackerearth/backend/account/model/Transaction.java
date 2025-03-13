package com.devsu.hackerearth.backend.account.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.devsu.hackerearth.backend.account.model.enumeration.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends Base {

	private LocalDateTime date;
	private TransactionType type;
	private double amount;
	private double balance;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
}
