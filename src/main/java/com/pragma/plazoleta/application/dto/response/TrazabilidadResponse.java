package com.pragma.plazoleta.application.dto.response;

import java.time.LocalDateTime;

public record TrazabilidadResponse (String estadoAnterior, String estadoNuevo, LocalDateTime fecha){
}
