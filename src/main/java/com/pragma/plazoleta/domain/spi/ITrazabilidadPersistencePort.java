package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Trazabilidad;

import java.util.List;
import java.util.UUID;

public interface ITrazabilidadPersistencePort {
    Trazabilidad guardar(Trazabilidad trazabilidad);
    List<Trazabilidad> buscarPorPedidoYCliente(UUID idPedido, UUID idCliente);
}
