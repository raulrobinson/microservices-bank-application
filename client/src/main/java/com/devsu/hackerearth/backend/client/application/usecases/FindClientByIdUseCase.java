package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.GlobalException;
import org.springframework.stereotype.Service;

@Service
public class FindClientByIdUseCase {

    private final ClientRepository clientRepository;

    public FindClientByIdUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain handle(Long id) {
        ClientDomain client = clientRepository.getById(id);
        if (client == null) {
            throw new GlobalException("Client not found");
        }
        return client;
    }
}
