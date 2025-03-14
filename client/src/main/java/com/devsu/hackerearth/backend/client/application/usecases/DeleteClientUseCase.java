package com.devsu.hackerearth.backend.client.application.usecases;

import com.devsu.hackerearth.backend.client.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientUseCase {

    private final ClientRepository clientRepository;

    public DeleteClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void execute(Long id) {
        clientRepository.deleteById(id);
    }
}
