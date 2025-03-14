package com.devsu.hackerearth.backend.client.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class PersonEntity extends Base {

	private String name;

	@Column(unique = true)
	private String dni;

	private String gender;
	private int age;
	private String address;
	private String phone;
}
