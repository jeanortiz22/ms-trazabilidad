package com.pragma.plazoleta.application.dto.response;

import java.util.List;

public record ReporteEficienciaResponse(
        List<EficienciaPedidoResponse> pedidos,
        List<RankingEmpleadoResponse> ranking
) {
}