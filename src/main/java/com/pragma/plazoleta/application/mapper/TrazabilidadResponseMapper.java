package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.TrazabilidadResponse;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrazabilidadResponseMapper {
    TrazabilidadResponse toResponse(Trazabilidad trazabilidad);
}
