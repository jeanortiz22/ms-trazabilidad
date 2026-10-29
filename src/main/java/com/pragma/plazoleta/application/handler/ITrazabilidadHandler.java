package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RegistrarTrazabilidadRequest;
import com.pragma.plazoleta.application.dto.response.TrazabilidadResponse;

import java.util.List;
import java.util.UUID;

public interface ITrazabilidadHandler {
    void registrar(RegistrarTrazabilidadRequest request);
    List<TrazabilidadResponse> consultarHistorial(UUID idPedido, UUID idCliente);
}
