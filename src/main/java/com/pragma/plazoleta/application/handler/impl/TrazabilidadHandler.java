package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RegistrarTrazabilidadRequest;
import com.pragma.plazoleta.application.dto.response.ReporteEficienciaResponse;
import com.pragma.plazoleta.application.dto.response.TrazabilidadResponse;
import com.pragma.plazoleta.application.handler.ITrazabilidadHandler;
import com.pragma.plazoleta.application.mapper.EficienciaResponseMapper;
import com.pragma.plazoleta.application.mapper.TrazabilidadRequestMapper;
import com.pragma.plazoleta.application.mapper.TrazabilidadResponseMapper;
import com.pragma.plazoleta.domain.api.ITrazabilidadServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrazabilidadHandler implements ITrazabilidadHandler {

    private final ITrazabilidadServicePort trazabilidadServicePort;
    private final TrazabilidadResponseMapper trazabilidadResponseMapper;
    private final TrazabilidadRequestMapper trazabilidadRequestMapper;
    private final EficienciaResponseMapper eficienciaResponseMapper;

    @Override
    public void registrar(RegistrarTrazabilidadRequest request) {
        trazabilidadServicePort.registrar(trazabilidadRequestMapper.toTrazabilidad(request));
    }

    @Override
    public List<TrazabilidadResponse> consultarHistorial(UUID idPedido, UUID idCliente) {
        return trazabilidadServicePort.consultarHistorial(idPedido, idCliente)
                .stream()
                .map(trazabilidadResponseMapper::toResponse)
                .toList();
    }

    @Override
    public ReporteEficienciaResponse calcularEficiencia(UUID idRestaurante, UUID idPropietario) {
        return eficienciaResponseMapper.toResponse(trazabilidadServicePort.calcularEficiencia(idRestaurante, idPropietario));
    }
}
