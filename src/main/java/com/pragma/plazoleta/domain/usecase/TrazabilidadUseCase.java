package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoleta.domain.exception.TrazabilidadNoEncontradaException;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort trazabilidadPersistencePort;

    public TrazabilidadUseCase(ITrazabilidadPersistencePort trazabilidadPersistencePort) {
        this.trazabilidadPersistencePort = trazabilidadPersistencePort;
    }

    @Override
    public void registrar(Trazabilidad trazabilidad) {
        trazabilidad.setFecha(LocalDateTime.now());
        trazabilidadPersistencePort.guardar(trazabilidad);
    }

    @Override
    public List<Trazabilidad> consultarHistorial(UUID idPedido, UUID idCliente) {
        List<Trazabilidad> historial = trazabilidadPersistencePort.buscarPorPedidoYCliente(idPedido, idCliente);
        if (historial.isEmpty()) {
            throw new TrazabilidadNoEncontradaException();
        }
        return historial;
    }
}
