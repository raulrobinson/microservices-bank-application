package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.shared.exception.NoContentException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllClientsUseCase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GetAllClientsUseCase.class);

    private final ClientRepository clientRepository;

    public GetAllClientsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDomain> handle() {
        List<ClientDomain> clients = clientRepository.getAll();
        if (clients.isEmpty()) throw new NoContentException("No clients found");
        log.info("Found {} clients", clients.size());
        return clients;
    }
}
