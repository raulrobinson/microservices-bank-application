package com.devsu.hackerearth.backend.client.domain.repository;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.application.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;

import java.util.List;

public interface ClientRepository {

    List<ClientDomain> getAll();
    ClientDomain getByClientCode(String clientCode);
    ClientDomain create(ClientRequestDto client);
    ClientDomain update(String clientCode, ClientRequestDto clientDto);
    ClientDomain partialUpdate(String clientCode, PartialClientDto client);
    void deleteByClientCode(String clientCode);
}
