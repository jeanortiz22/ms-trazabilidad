package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.ReporteEficiencia;
import com.pragma.plazoleta.domain.model.Trazabilidad;

import java.util.List;
import java.util.UUID;

public interface ITrazabilidadServicePort {
    void registrar(Trazabilidad trazabilidad);
    List<Trazabilidad> consultarHistorial(UUID idPedido, UUID idCliente);
    ReporteEficiencia calcularEficiencia(UUID idRestaurante, UUID idPropietario);
}
