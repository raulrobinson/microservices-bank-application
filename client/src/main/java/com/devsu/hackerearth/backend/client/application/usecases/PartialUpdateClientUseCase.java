package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.application.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class PartialUpdateClientUseCase {

    private final ClientRepository clientRepository;

    public PartialUpdateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain execute(String clientCode, PartialClientDto client) {
        return clientRepository.partialUpdate(clientCode, client);
    }
}
