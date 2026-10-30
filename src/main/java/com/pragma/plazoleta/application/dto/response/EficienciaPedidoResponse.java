package com.pragma.plazoleta.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record EficienciaPedidoResponse(
        UUID idPedido,
        UUID idEmpleado,
        LocalDateTime inicio,
        LocalDateTime fin,
        Long duracionSegundos
) {
}
