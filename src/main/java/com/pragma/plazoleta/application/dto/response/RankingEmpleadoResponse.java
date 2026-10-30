package com.pragma.plazoleta.application.dto.response;

import java.util.UUID;

public record RankingEmpleadoResponse(
        UUID idEmpleado,
        double tiempoMedioSegundos,
        int pedidosEntregados
) {
}
