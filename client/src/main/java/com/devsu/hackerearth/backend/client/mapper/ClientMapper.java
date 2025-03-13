package com.devsu.hackerearth.backend.client.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Component
public class ClientMapper {

    public ClientDto clientEntityToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setDni(client.getDni());
        clientDto.setAge(client.getAge());
        clientDto.setAddress(client.getAddress());
        clientDto.setPhone(client.getPhone());
        clientDto.setGender(client.getGender());
        clientDto.setName(client.getName());
        clientDto.setActive(client.isActive());
        clientDto.setPassword(client.getPassword());

        return clientDto;
    }
    
}
