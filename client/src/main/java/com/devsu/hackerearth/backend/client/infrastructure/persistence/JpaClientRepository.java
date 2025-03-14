package com.devsu.hackerearth.backend.client.infrastructure.persistence;

import java.util.List;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.application.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.GlobalException;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.application.mapper.ClientMapper;

@Service
public class JpaClientRepository implements ClientRepository {

	private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger("TRANSACTION_AUDIT");	

	private final SpringDataClientRepository springDataClientRepository;
	private final ClientMapper clientMapper;

	public JpaClientRepository(SpringDataClientRepository springDataClientRepository, ClientMapper clientMapper) {
		this.springDataClientRepository = springDataClientRepository;
		this.clientMapper = clientMapper;
	}

	// Get all clients
	@Override
	public List<ClientDomain> getAll() {
		return springDataClientRepository.findAll().stream()
			.map(clientMapper::toClientDomain)
			.toList();
	}

	// Get clients by id
	@Override
	public ClientDomain getById(Long id) {
		return clientMapper.toClientDomain(springDataClientRepository
				.findById(id)
				.orElse(null));
	}

	// Create client
	@Override
	public ClientDomain create(ClientRequestDto client) {
        ClientEntity findClientEntityByDni = springDataClientRepository.findClientByDni(client.getDni());
        if (findClientEntityByDni != null) throw new GlobalException("Client already exists with ID " + client.getDni());
		ClientEntity savedClientEntity = springDataClientRepository.save(clientMapper.toClientEntity(client));
		if (savedClientEntity.getId() != 0) {
			transactionLogger.info("Client with ID {} and DNI {} saved", savedClientEntity.getId(), savedClientEntity.getDni());
			return clientMapper.toClientDomain(savedClientEntity);
		}
		throw new GlobalException("Client not saved");
	}

	// Update client
	@Override
	public ClientDomain update(Long id, ClientRequestDto clientDto) {
		// Check if client Exists by ID and DNI match
		ClientEntity clientByDniAndId = springDataClientRepository.findClientByDniAndId(clientDto.getDni(), id);
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
			ClientEntity updatedClient = springDataClientRepository.save(clientByDniAndId);
			if (updatedClient.getId() != 0) {
				transactionLogger.info("Client with ID {} updated", updatedClient.getId());
				return clientMapper.toClientDomain(updatedClient);
			}

			throw new GlobalException("Client not updated");
		}
	}

    // Partial update account
	@Override
    public ClientDomain partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Check if exists client by ID
		ClientEntity clientById = springDataClientRepository.findById(id).orElse(null);
        if (clientById == null)
            throw new GlobalException("Client with ID " + id + " not found");

		// Update Client
		clientById.setActive(partialClientDto.isActive());

		// Save updated Client
		ClientEntity updatedClient = springDataClientRepository.save(clientById);
		if (updatedClient.getId() != 0) {
			transactionLogger.info("Client with ID {} status updated", updatedClient.getId());
			return clientMapper.toClientDomain(updatedClient);
		}

		throw new GlobalException("Client not status updated");
    }

	// Delete client
	@Override
	public void deleteById(Long id) {
		if (springDataClientRepository.existsById(id)) {
            springDataClientRepository.deleteById(id);
            transactionLogger.info("Client with ID {} deleted", id);
        } else {
            throw new GlobalException("Client with ID " + id + " not found");
        }
	}

}
