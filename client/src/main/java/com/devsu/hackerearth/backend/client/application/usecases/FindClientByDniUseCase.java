package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FindClientByDniUseCase {

    private static final Logger transactionLogger = org.slf4j.LoggerFactory.getLogger(FindClientByDniUseCase.class);

    private final ClientRepository clientRepository;

    public FindClientByDniUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain handle(String dni) {
        ClientDomain client = clientRepository.getClientByDni(dni);
        if (client == null) throw new NotFoundException("Client with DNI: " + dni + " not found");
        transactionLogger.info("Client with DNI: {} found", dni);
        return client;
    }
}
