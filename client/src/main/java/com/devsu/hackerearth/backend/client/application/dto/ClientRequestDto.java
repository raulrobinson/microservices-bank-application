package com.devsu.hackerearth.backend.client.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ClientRequestDto", description = "Client request data transfer object")
public class ClientRequestDto {

	@Schema(description = "Client DNI", example = "12345678")
	private String dni;

	@Schema(description = "Client name", example = "John Doe")
	private String name;

	@Schema(description = "Password", example = "123456")
	private String password;

	@Schema(description = "Client Gender", example = "M")
	private String gender;

	@Schema(description = "Client age", example = "25")
	private int age;

	@Schema(description = "Client address", example = "Av. Los Pinos 123")
	private String address;

	@Schema(description = "Client phone", example = "987654321")
	private String phone;

	@Schema(description = "Status of the client", example = "true")
	private boolean isActive;
    
}
