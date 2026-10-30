package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.infraestructure.out.mongodb.document.TrazabilidadDocument;

import java.util.List;
import java.util.UUID;

public interface ITrazabilidadPersistencePort {
    Trazabilidad guardar(Trazabilidad trazabilidad);
    List<Trazabilidad> buscarPorPedidoYCliente(UUID idPedido, UUID idCliente);
    List<Trazabilidad> buscarPorRestauranteYPropietario(UUID idRestaurante, UUID idPropietario);
}
