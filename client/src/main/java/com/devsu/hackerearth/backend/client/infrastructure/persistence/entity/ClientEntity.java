package com.devsu.hackerearth.backend.client.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
@Table(name = "clients")
public class ClientEntity extends PersonEntity {
	private String password;

	@Column(name = "is_active")
	private boolean isActive;
}
