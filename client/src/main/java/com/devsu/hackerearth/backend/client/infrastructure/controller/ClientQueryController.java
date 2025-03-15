package com.devsu.hackerearth.backend.client.infrastructure.controller;

import com.devsu.hackerearth.backend.client.application.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.application.dto.ClientDto;
import com.devsu.hackerearth.backend.client.application.usecases.GetAllClientsUseCase;
import com.devsu.hackerearth.backend.client.application.usecases.FindClientByIdUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${controller.properties.base-path}/clients")
@Tag(name = "Client", description = "Client operations")
public class ClientQueryController {

    private final GetAllClientsUseCase getAll;
    private final FindClientByIdUseCase getByClientCode;

    public ClientQueryController(GetAllClientsUseCase getAll, FindClientByIdUseCase getByClientCode) {
        this.getAll = getAll;
        this.getByClientCode = getByClientCode;
    }

    /**
     * Get all clients
     * @return List of ClientDto
     */
    @GetMapping
    @Operation(summary = "Get all clients", description = "Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of clients"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ClientDto>> getAll() {
        List<ClientDto> clients = ClientMapper.INSTANCE.toClientDtoList(getAll.handle());
        if (clients.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    /**
     * Get client by client Code
     * @param clientCode (required)
     * @return ClientDto (found)
     */
    @GetMapping("/{clientCode}")
    @Operation(summary = "Get client by client Code", description = "Get client by client Code")
    @Parameter(name = "clientCode", description = "Client ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @ApiResponse(responseCode = "200", description = "Client")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ClientDto> get(@PathVariable String clientCode) {
        ClientDto client = ClientMapper.INSTANCE.toClientDto(getByClientCode.handle(clientCode));
        if (client == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}
