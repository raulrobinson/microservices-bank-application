package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateClientUseCase {

    private final ClientRepository clientRepository;

    public UpdateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDomain execute(Long id, ClientRequestDto client) {
        return clientRepository.update(id, client);
    }
}
