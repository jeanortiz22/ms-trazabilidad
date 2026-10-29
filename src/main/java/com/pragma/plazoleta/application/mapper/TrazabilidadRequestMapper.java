package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.RegistrarTrazabilidadRequest;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrazabilidadRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    Trazabilidad toTrazabilidad(RegistrarTrazabilidadRequest request);

}
