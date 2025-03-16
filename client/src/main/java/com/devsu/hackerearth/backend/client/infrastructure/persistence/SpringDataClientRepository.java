package com.devsu.hackerearth.backend.client.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;

import java.util.UUID;

@Repository
public interface SpringDataClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findClientEntityById(UUID id);

    ClientEntity findClientByDni(String dni);

    ClientEntity findClientByDniAndId(String dni, UUID id);

    ClientEntity findClientEntityByDniAndClientCode(String dni, String clientCode);

    ClientEntity findClientEntityByClientCode(String clientCode);

    ClientEntity findClientEntityByDni(String dni);
}
