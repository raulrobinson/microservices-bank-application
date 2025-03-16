package com.devsu.hackerearth.backend.client.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;

@Repository
public interface SpringDataClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findClientByDni(String dni);

    ClientEntity findClientEntityByDniAndClientCode(String dni, String clientCode);

    ClientEntity findClientEntityByClientCode(String clientCode);

}
