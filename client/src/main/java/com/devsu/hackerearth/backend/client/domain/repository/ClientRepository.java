package com.devsu.hackerearth.backend.client.domain.repository;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.application.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;

import java.util.List;

public interface ClientRepository {

    List<ClientDomain> getAll();
    ClientDomain getById(Long id);
    ClientDomain create(ClientRequestDto client);
    ClientDomain update(Long id, ClientRequestDto client);
    ClientDomain partialUpdate(Long id, PartialClientDto client);
    void deleteById(Long id);
}
