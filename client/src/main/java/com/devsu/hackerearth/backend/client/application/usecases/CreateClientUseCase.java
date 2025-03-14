package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {

    private final ClientRepository clientRepository;

    public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain execute(ClientRequestDto client) {
        return clientRepository.create(client);
    }

}
