package com.devsu.hackerearth.backend.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsu.hackerearth.backend.client.application.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.entity.ClientEntity;
import com.devsu.hackerearth.backend.client.application.dto.ClientDto;
import com.devsu.hackerearth.backend.client.application.dto.ClientRequestDto;
import com.devsu.hackerearth.backend.client.application.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.JpaClientRepository;
import com.devsu.hackerearth.backend.client.infrastructure.persistence.SpringDataClientRepository;

@ExtendWith(MockitoExtension.class)
public class sampleTest {

//    @Mock
//    private ClientMapper clientMapper;
//
//    @InjectMocks
//    private JpaClientRepository clientService;
//
//    @Mock
//    private SpringDataClientRepository springDataClientRepository;
//
//    private ClientEntity clientEntity;
//    private ClientDto clientDto;
//    private ClientRequestDto clientRequestDto;
//    private PartialClientDto partialClientDto;
//
//    @BeforeEach
//    void setUp() {
//        clientEntity = new ClientEntity();
//        clientDto = new ClientDto();
//        clientRequestDto = new ClientRequestDto();
//
//        //clientEntity.setId("550e8400-e29b-41d4-a716-446655440000");
//        clientEntity.setName("Raul Bolivar");
//        clientEntity.setDni("72111");
//        clientEntity.setGender("Male");
//        clientEntity.setAge(51);
//        clientEntity.setAddress("CL 3 51B-188");
//        clientEntity.setPhone("3508619025");
//        clientEntity.setPassword("password");
//        clientEntity.setActive(true);
//
//        clientDto.setName("Raul Bolivar");
//        clientDto.setDni("72111");
//        clientDto.setGender("Male");
//        clientDto.setAge(51);
//        clientDto.setAddress("CL 3 51B-188");
//        clientDto.setPhone("3508619025");
//        clientDto.setPassword("password");
//        clientDto.setActive(true);
//
//        clientRequestDto.setName("Raul Bolivar");
//        clientRequestDto.setDni("72111");
//        clientRequestDto.setGender("Male");
//        clientRequestDto.setAge(51);
//        clientRequestDto.setAddress("CL 3 51B-188");
//        clientRequestDto.setPhone("3508619025");
//        clientRequestDto.setPassword("password");
//        clientRequestDto.setActive(true);
//
//        partialClientDto = new PartialClientDto();
//        partialClientDto.setActive(true);
//    }

//    @Test
//    void testGetAllClients_Success() {
//        when(springDataClientRepository.findAll()).thenReturn(Collections.singletonList(client));
//        when(clientMapper.clientEntityToDto(client)).thenReturn(clientDto);
//
//        List<ClientDto> clients = clientService.getAll();
//
//        assertNotNull(clients);
//        assertFalse(clients.isEmpty());
//        assertEquals(1, clients.size());
//
//        verify(springDataClientRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetAllClients_Empty() {
//        when(springDataClientRepository.findAll()).thenReturn(Collections.emptyList());
//
//        assertThrows(NoContentException.class, () -> clientService.getAll());
//
//        verify(springDataClientRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetById_Success() {
//        when(springDataClientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
//        when(clientMapper.clientEntityToDto(client)).thenReturn(clientDto);
//
//        ClientDto clientById = clientService.getById(1L);
//
//        assertNotNull(clientById);
//        assertEquals(clientDto, clientById);
//
//        verify(springDataClientRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testGetById_NotFound() {
//        when(springDataClientRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//
//        assertThrows(GlobalException.class, () -> clientService.getById(1L));
//
//        verify(springDataClientRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testCreateClient_Success() {
//        when(springDataClientRepository.findClientByDni(clientRequestDto.getDni())).thenReturn(null);
//
//        when(springDataClientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
//            Client savedClient = invocationOnMock.getArgument(0);
//            savedClient.setId(1L);
//            System.out.println("Client saved with ID: " + savedClient.getId());
//            return savedClient;
//        });
//
//        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);
//
//        ClientDto createdClient = clientService.create(clientRequestDto);
//
//        assertNotNull(createdClient, "Client is null");
//        assertEquals(clientDto, createdClient, "Client is not equal");
//
//        verify(springDataClientRepository, times(1)).findClientByDni(clientRequestDto.getDni());
//        verify(springDataClientRepository, times(1)).save(any(Client.class));
//    }
//
//    @Test
//    void testCreateClient_AlreadyExists() {
//        when(springDataClientRepository.findClientByDni(clientRequestDto.getDni())).thenReturn(client);
//
//        assertThrows(GlobalException.class, () -> clientService.create(clientRequestDto));
//
//        verify(springDataClientRepository, times(1)).findClientByDni(clientRequestDto.getDni());
//    }
//
//    @Test
//    void testUpdateClient_Success() {
//        when(springDataClientRepository.findClientByDniAndId(clientRequestDto.getDni(), 1L)).thenReturn(client);
//
//        when(springDataClientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
//            Client savedClient = invocationOnMock.getArgument(0);
//            savedClient.setId(1L);
//            System.out.println("Client updated with ID: " + savedClient.getId());
//            return savedClient;
//        });
//
//        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);
//
//        ClientDto updatedClient = clientService.update(1L, clientRequestDto);
//
//        assertNotNull(updatedClient, "Client is null");
//        assertEquals(clientDto, updatedClient, "Client is not equal");
//
//        verify(springDataClientRepository, times(1)).findClientByDniAndId(clientRequestDto.getDni(), 1L);
//        verify(springDataClientRepository, times(1)).save(any(Client.class));
//    }
//
//    @Test
//    void testUpdateClient_NotMatch() {
//        when(springDataClientRepository.findClientByDniAndId(clientRequestDto.getDni(), 1L)).thenReturn(null);
//
//        assertThrows(GlobalException.class, () -> clientService.update(1L, clientRequestDto));
//
//        verify(springDataClientRepository, times(1)).findClientByDniAndId(clientRequestDto.getDni(), 1L);
//    }
//
//    @Test
//    void testPartialUpdate_Success() {
//        when(springDataClientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
//
//        when(springDataClientRepository.save(any(Client.class))).thenAnswer(invocationOnMock -> {
//            Client savedClient = invocationOnMock.getArgument(0);
//            savedClient.setId(1L);
//            System.out.println("Client updated with ID: " + savedClient.getId());
//            return savedClient;
//        });
//
//        when(clientMapper.clientEntityToDto(any(Client.class))).thenReturn(clientDto);
//
//        ClientDto updatedClient = clientService.partialUpdate(1L, partialClientDto);
//
//        assertNotNull(updatedClient, "Client is null");
//        assertEquals(clientDto, updatedClient, "Client is not equal");
//
//        verify(springDataClientRepository, times(1)).findById(1L);
//        verify(springDataClientRepository, times(1)).save(any(Client.class));
//    }
//
//    @Test
//    void testPartialUpdate_NotFound() {
//        when(springDataClientRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//
//        assertThrows(GlobalException.class, () -> clientService.partialUpdate(1L, partialClientDto));
//
//        verify(springDataClientRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteClient_Success() {
//        when(springDataClientRepository.existsById(1L)).thenReturn(true);
//        doNothing().when(springDataClientRepository).deleteById(1L);
//
//        assertDoesNotThrow(() -> clientService.deleteById(1L));
//
//        verify(springDataClientRepository, times(1)).existsById(1L);
//        verify(springDataClientRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteClient_NotFound() {
//        when(springDataClientRepository.existsById(1L)).thenReturn(false);
//
//        assertThrows(GlobalException.class, () -> clientService.deleteById(1L));
//
//        verify(springDataClientRepository, times(1)).existsById(1L);
//    }
}
