package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	// api/clients
	// Get all clients
	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		List<ClientDto> clients = clientService.getAll();
		if (clients.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}

	// api/clients/{id}
	// Get clients by id
	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		ClientDto client = clientService.getById(id);
		if (client == null) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(client, HttpStatus.OK); 
	}

	// api/clients
	// Create client
	@PostMapping
	public ResponseEntity<ClientDto> create(@RequestBody ClientRequestDto clientDto) {
		ClientDto client = clientService.create(clientDto);
		if (client == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(client, HttpStatus.CREATED); 
	}

	// api/clients/{id}
	// Update client
	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, 
											@RequestBody ClientRequestDto clientDto) {
		ClientDto client = clientService.update(id, clientDto);
		if (client == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	// api/accounts/{id}
	// Partial update accounts
	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, 
												   @RequestBody PartialClientDto partialClientDto) {		
		ClientDto client = clientService.partialUpdate(id, partialClientDto);
		if (client == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	// api/clients/{id}
	// Delete client
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {		
		clientService.deleteById(id);
	}
}
