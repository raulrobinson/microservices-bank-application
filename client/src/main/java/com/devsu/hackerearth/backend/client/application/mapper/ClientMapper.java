package com.devsu.hackerearth.backend.client.application.mapper;

import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;

import com.devsu.hackerearth.backend.client.application.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientMapper {

    ClientMapper INSTANCE = new ClientMapperImpl();

    ClientDomain toClientDomain(ClientEntity clientEntity);
    ClientEntity toClientEntity(ClientRequestDto clientRequestDto);
    ClientDto toClientDto(ClientDomain clientDomain);
    List<ClientDto> toClientDtoList(List<ClientDomain> clientDomainList);
}
