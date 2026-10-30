package com.pragma.plazoleta.domain.model;

import java.util.UUID;

public record RankingEmpleado(
        UUID idEmpleado,
        double tiempoMedioSegundos,
        int pedidosEntregados
) {
}
