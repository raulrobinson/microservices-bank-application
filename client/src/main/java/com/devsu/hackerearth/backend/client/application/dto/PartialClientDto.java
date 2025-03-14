package com.devsu.hackerearth.backend.client.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PartialClientDto", description = "Partial Client DTO")
public class PartialClientDto {

    @Schema(description = "Status of the client", example = "false")
    private boolean isActive;
}
