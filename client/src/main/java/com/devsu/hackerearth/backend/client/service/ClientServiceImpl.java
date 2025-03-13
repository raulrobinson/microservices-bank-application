package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exception.GlobalException;
import com.devsu.hackerearth.backend.client.exception.NoContentException;
import com.devsu.hackerearth.backend.client.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");	

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	// Get all clients
	@Override
	public List<ClientDto> getAll() {
		List<ClientDto> clients = clientRepository.findAll().stream()
            .map(clientMapper::clientEntityToDto)
            .collect(Collectors.toList());
        if (clients.isEmpty()) 
            throw new NoContentException("No clients found");        

		transactionLogger.info("Found {} clients", clients.size());
        return clients;
	}

	// Get clients by id
	@Override
	public ClientDto getById(Long id) {
		Client client = clientRepository.findById(id).orElse(null);
        if (client == null) 
            throw new GlobalException("Client with ID " + id + " not found");

		transactionLogger.info("Account with ID {}", id);
        return clientMapper.clientEntityToDto(client);
	}

	// Create client
	@Override
	public ClientDto create(ClientRequestDto clientDto) {
		// Check if client already exists
        Client client = clientRepository.findClientByDni(clientDto.getDni());
        if (client != null) 
            throw new GlobalException("Client already exists with ID " + clientDto.getDni());

		// Create new Client
		Client newClient = new Client();
		newClient.setName(clientDto.getName());
		newClient.setDni(clientDto.getDni());
		newClient.setGender(clientDto.getGender());
		newClient.setAge(clientDto.getAge());
		newClient.setPhone(clientDto.getPhone());
		newClient.setAddress(clientDto.getAddress());
		newClient.setPassword(clientDto.getPassword());
		newClient.setActive(clientDto.isActive());

		// Save new Client
		Client savedClient = clientRepository.save(newClient);
		if (savedClient.getId() != 0) {
			transactionLogger.info("Client with ID {} saved", savedClient.getId());
			return clientMapper.clientEntityToDto(savedClient);
		}

		throw new GlobalException("Client not saved");
	}

	// Update client
	@Override
	public ClientDto update(Long id, ClientRequestDto clientDto) {
		// Check if client Exists by ID and DNI match
		Client clientByDniAndId = clientRepository.findClientByDniAndId(clientDto.getDni(), id);
		if (clientByDniAndId == null) {
			throw new GlobalException("Client with DNI: " + clientDto.getDni() + " and ID: " + id + " not match or not found");
		} else {
			// Update Client
			clientByDniAndId.setName(clientDto.getName());
			clientByDniAndId.setDni(clientDto.getDni());
			clientByDniAndId.setGender(clientDto.getGender());
			clientByDniAndId.setAge(clientDto.getAge());
			clientByDniAndId.setPhone(clientDto.getPhone());
			clientByDniAndId.setAddress(clientDto.getAddress());
			clientByDniAndId.setPassword(clientDto.getPassword());
			clientByDniAndId.setActive(clientDto.isActive());
	
			// Save updated Client
			Client updatedClient = clientRepository.save(clientByDniAndId);
			if (updatedClient.getId() != 0) {
				transactionLogger.info("Client with ID {} updated", updatedClient.getId());
				return clientMapper.clientEntityToDto(updatedClient);
			}
	
			throw new GlobalException("Client not updated");
		}
	}

    // Partial update account
	@Override
    public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Check if exists client by ID
		Client clientById = clientRepository.findById(id).orElse(null);
        if (clientById == null) 
            throw new GlobalException("Client with ID " + id + " not found");

		// Update Client
		clientById.setActive(partialClientDto.isActive());

		// Save updated Client
		Client updatedClient = clientRepository.save(clientById);
		if (updatedClient.getId() != 0) {
			transactionLogger.info("Client with ID {} status updated", updatedClient.getId());
			return clientMapper.clientEntityToDto(updatedClient);
		}

		throw new GlobalException("Client not saved");
    }

	// Delete client
	@Override
	public void deleteById(Long id) {
		if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            transactionLogger.info("Client with ID {} deleted", id);
        } else {
            throw new GlobalException("Client with ID " + id + " not found");
        }
	}

}
