package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.devsu.hackerearth.backend.account.model.enumeration.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Base {

    private String number;
	private AccountType type;
	private double initialAmount;
	private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;
}
