package com.pragma.plazoleta.infraestructure.out.mongodb.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "trazabilidad")
@Getter
@Setter
@NoArgsConstructor
public class TrazabilidadDocument {
    @Id
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
