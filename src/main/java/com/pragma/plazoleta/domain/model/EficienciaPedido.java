package com.pragma.plazoleta.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record EficienciaPedido(
        UUID idPedido,
        UUID idEmpleado,
        LocalDateTime inicio,
        LocalDateTime fin,
        Long duracionSegundos

) {
}
