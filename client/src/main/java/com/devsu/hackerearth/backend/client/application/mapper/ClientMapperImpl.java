package com.devsu.hackerearth.backend.client.application.mapper;

import com.devsu.hackerearth.backend.client.application.dto.ClientDto;
import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.domain.model.ClientDomain;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapperImpl implements ClientMapper {

    public ClientDomain toClientDomain(ClientEntity clientEntity) {
        if (clientEntity == null) {
            return null;
        } else {
            ClientDomain clientDomain = new ClientDomain();
            clientDomain.setId(clientEntity.getId());
            clientDomain.setDni(clientEntity.getDni());
            clientDomain.setName(clientEntity.getName());
            clientDomain.setPassword(clientEntity.getPassword());
            clientDomain.setGender(clientEntity.getGender());
            clientDomain.setAge(clientEntity.getAge());
            clientDomain.setAddress(clientEntity.getAddress());
            clientDomain.setPhone(clientEntity.getPhone());
            clientDomain.setActive(clientEntity.isActive());
            return clientDomain;
        }
    }

    public ClientEntity toClientEntity(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            return null;
        } else {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setName(clientRequestDto.getName());
            clientEntity.setDni(clientRequestDto.getDni());
            clientEntity.setGender(clientRequestDto.getGender());
            clientEntity.setAge(clientRequestDto.getAge());
            clientEntity.setAddress(clientRequestDto.getAddress());
            clientEntity.setPhone(clientRequestDto.getPhone());
            clientEntity.setPassword(clientRequestDto.getPassword());
            clientEntity.setActive(clientRequestDto.isActive());
            return clientEntity;
        }
    }

    public ClientDto toClientDto(ClientDomain clientDomain) {
        if (clientDomain == null) {
            return null;
        } else {
            ClientDto clientDto = new ClientDto();
            clientDto.setId(clientDomain.getId());
            clientDto.setDni(clientDomain.getDni());
            clientDto.setName(clientDomain.getName());
            clientDto.setPassword(clientDomain.getPassword());
            clientDto.setGender(clientDomain.getGender());
            clientDto.setAge(clientDomain.getAge());
            clientDto.setAddress(clientDomain.getAddress());
            clientDto.setPhone(clientDomain.getPhone());
            clientDto.setActive(clientDomain.isActive());
            return clientDto;
        }
    }

    public List<ClientDto> toClientDtoList(List<ClientDomain> clientDomainList) {
        if (clientDomainList == null) {
            return null;
        } else {
            List<ClientDto> list = new ArrayList<>(clientDomainList.size());

            for(ClientDomain clientDomain : clientDomainList) {
                list.add(this.toClientDto(clientDomain));
            }

            return list;
        }
    }

}
