package com.devsu.hackerearth.backend.client.infrastructure.persistence.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class Base {

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(columnDefinition = "UUID", updatable = false, nullable = false)
	private UUID id;

//	@PrePersist
//	protected void generateId() {
//		if (id == null) {
//			id = UUID.randomUUID();
//		}
//	}
}
