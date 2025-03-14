package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.GlobalException;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FindClientByIdUseCase {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger(FindClientByIdUseCase.class);

    private final ClientRepository clientRepository;

    public FindClientByIdUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain handle(Long id) {
        ClientDomain client = clientRepository.getById(id);
        if (client == null) throw new NotFoundException("Client with ID " + id + " not found");
        transactionLogger.info("Client with ID {} found", id);
        return client;
    }
}
