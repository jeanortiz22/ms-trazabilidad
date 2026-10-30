package com.pragma.plazoleta.domain.model;

import java.util.List;

public record ReporteEficiencia(
        List<EficienciaPedido> pedidos,
        List<RankingEmpleado> ranking
) {
}
