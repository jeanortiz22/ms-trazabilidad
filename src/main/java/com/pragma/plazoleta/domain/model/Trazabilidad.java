package com.pragma.plazoleta.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Trazabilidad {
    private String id;
    private UUID idPedido;
    private UUID idCliente;
    private String correoCliente;
    private UUID idRestaurante;
    private UUID idPropietario;
    private LocalDateTime fecha;
    private String estadoAnterior;
    private String estadoNuevo;
    private UUID idEmpleado;
    private String correoEmpleado;
}
