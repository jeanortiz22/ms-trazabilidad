package com.pragma.plazoleta.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class RegistrarTrazabilidadRequest {
    @NotNull(message = "El pedido es obligatorio")
    private UUID idPedido;

    @NotNull(message = "El cliente es obligatorio")
    private UUID idCliente;

    private String correoCliente;

    @NotNull(message = "El restaurante es obligatorio")
    private UUID idRestaurante;

    @NotNull(message = "El propietario es obligatorio")
    private UUID idPropietario;

    private String estadoAnterior;

    @NotBlank(message = "El estado nuevo es obligatorio")
    private String estadoNuevo;

    private UUID idEmpleado;
    private String correoEmpleado;

}
