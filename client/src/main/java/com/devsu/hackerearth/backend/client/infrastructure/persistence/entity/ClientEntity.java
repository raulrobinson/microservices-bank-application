package com.devsu.hackerearth.backend.client.infrastructure.persistence.entity;

import com.devsu.hackerearth.backend.client.infrastructure.persistence.enumeration.ClientType;
import jakarta.persistence.*;

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

//	@Enumerated(EnumType.STRING)
	@Column(name = "client_type", nullable = false, length = 20)
	private String clientType;

	@Column(name = "client_code", unique = true, nullable = false, updatable = false, length = 20)
	private String clientCode;

	@PrePersist
	protected void generateCode() {
		this.clientCode = "CLI-" + System.currentTimeMillis();
	}
}
