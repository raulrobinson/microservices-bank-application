package com.devsu.hackerearth.backend.client;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsu.hackerearth.backend.client.exception.GlobalException;
import com.devsu.hackerearth.backend.client.exception.NoContentException;
import com.devsu.hackerearth.backend.client.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientServiceImpl;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class sampleTest {

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private ClientDto clientDto;
    private ClientRequestDto clientRequestDto;
    private PartialClientDto partialClientDto;

    @BeforeEach
    void setUp() {
        client = new Client();
        clientDto = new ClientDto();
        clientRequestDto = new ClientRequestDto();

        client.setId(1L);
        client.setName("Raul Bolivar");
        client.setDni("72111");
        client.setGender("Male");
        client.setAge(51);
        client.setAddress("CL 3 51B-188");
        client.setPhone("3508619025");
        client.setPassword("password");
        client.setActive(true);

        clientDto.setName("Raul Bolivar");
        clientDto.setDni("72111");
        clientDto.setGender("Male");
        clientDto.setAge(51);
        clientDto.setAddress("CL 3 51B-188");
        clientDto.setPhone("3508619025");
        clientDto.setPassword("password");
        clientDto.setActive(true);

        clientRequestDto.setName("Raul Bolivar");
        clientRequestDto.setDni("72111");
        clientRequestDto.setGender("Male");
        clientRequestDto.setAge(51);
        clientRequestDto.setAddress("CL 3 51B-188");
        clientRequestDto.setPhone("3508619025");
        clientRequestDto.setPassword("password");
        clientRequestDto.setActive(true);

        partialClientDto = new PartialClientDto();
        partialClientDto.setActive(true);
    }

    @Test
    void testGetAllClients_Success() {
        when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        when(clientMapper.clientEntityToDto(client)).thenReturn(clientDto);

        List<ClientDto> clients = clientService.getAll();

        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals(1, clients.size());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetAllClients_Empty() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoContentException.class, () -> clientService.getAll());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Success() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
        when(clientMapper.clientEntityToDto(client)).thenReturn(clientDto);

        ClientDto clientById = clientService.getById(1L);

        assertNotNull(clientById);
        assertEquals(clientDto, clientById);

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(GlobalException.class, () -> clientService.getById(1L));

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateClient_Success() {
        when(clientRepository.findClientByDni(clientRequestDto.getDni())).thenReturn(null);

        when(clientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
            Client savedClient = invocationOnMock.getArgument(0);
            savedClient.setId(1L);
            System.out.println("Client saved with ID: " + savedClient.getId());
            return savedClient;
        });

        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);

        ClientDto createdClient = clientService.create(clientRequestDto);

        assertNotNull(createdClient, "Client is null");
        assertEquals(clientDto, createdClient, "Client is not equal");

        verify(clientRepository, times(1)).findClientByDni(clientRequestDto.getDni());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testCreateClient_AlreadyExists() {
        when(clientRepository.findClientByDni(clientRequestDto.getDni())).thenReturn(client);

        assertThrows(GlobalException.class, () -> clientService.create(clientRequestDto));

        verify(clientRepository, times(1)).findClientByDni(clientRequestDto.getDni());
    }

    @Test
    void testUpdateClient_Success() {
        when(clientRepository.findClientByDniAndId(clientRequestDto.getDni(), 1L)).thenReturn(client);

        when(clientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
            Client savedClient = invocationOnMock.getArgument(0);
            savedClient.setId(1L);
            System.out.println("Client updated with ID: " + savedClient.getId());
            return savedClient;
        });

        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);

        ClientDto updatedClient = clientService.update(1L, clientRequestDto);

        assertNotNull(updatedClient, "Client is null");
        assertEquals(clientDto, updatedClient, "Client is not equal");

        verify(clientRepository, times(1)).findClientByDniAndId(clientRequestDto.getDni(), 1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClient_NotMatch() {
        when(clientRepository.findClientByDniAndId(clientRequestDto.getDni(), 1L)).thenReturn(null);

        assertThrows(GlobalException.class, () -> clientService.update(1L, clientRequestDto));

        verify(clientRepository, times(1)).findClientByDniAndId(clientRequestDto.getDni(), 1L);
    }

    @Test
    void testPartialUpdate_Success() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));

        when(clientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
            Client savedClient = invocationOnMock.getArgument(0);
            savedClient.setId(1L);
            System.out.println("Client updated with ID: " + savedClient.getId());
            return savedClient;
        });

        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);

        ClientDto updatedClient = clientService.partialUpdate(1L, partialClientDto);

        assertNotNull(updatedClient, "Client is null");
        assertEquals(clientDto, updatedClient, "Client is not equal");

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testPartialUpdate_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(GlobalException.class, () -> clientService.partialUpdate(1L, partialClientDto));

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteClient_Success() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        assertDoesNotThrow(() -> clientService.deleteById(1L));

        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClient_NotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(GlobalException.class, () -> clientService.deleteById(1L));

        verify(clientRepository, times(1)).existsById(1L);
    }
}
