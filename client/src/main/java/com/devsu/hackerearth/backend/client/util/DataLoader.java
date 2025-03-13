package com.devsu.hackerearth.backend.client.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.service.ClientService;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public DataLoader(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // Client 1
        /*ClientRequestDto client1 = new ClientRequestDto();
        client1.setName("Raul Bolivar");
        client1.setDni("72111");
        client1.setGender("Male");
        client1.setAge(51);
        client1.setAddress("CL 3 51B-186");
        client1.setPhone("3508619025");
        client1.setActive(true);
        client1.setPassword("Password123!");
        clientService.create(client1);
        Client findClient1 = clientRepository.findClientByDni(client1.getDni());
        System.out.println("Client 1 Created NAME: " + findClient1.getName() + " DNI: " + findClient1.getDni() + " client_id: " + findClient1.getId());  

        // Client 2
        ClientRequestDto client2 = new ClientRequestDto();
        client2.setName("Robinson Navas");
        client2.setDni("72222");
        client2.setGender("Male");
        client2.setAge(51);
        client2.setAddress("CL 3 51B-225");
        client2.setPhone("3004524521");
        client2.setActive(true);
        client2.setPassword("Password123*");  
        clientService.create(client2);
        Client findClient2 = clientRepository.findClientByDni(client2.getDni());
        System.out.println("Client 2 Created NAME: " + findClient2.getName() + " DNI: " + findClient2.getDni() + " client_id: " + findClient2.getId());  */

    }
    
}
