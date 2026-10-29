package com.pragma.plazoleta.infraestructure.out.mongodb.mapper;

import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.infraestructure.out.mongodb.document.TrazabilidadDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrazabilidadDocumentMapper {
    TrazabilidadDocument toDocument(Trazabilidad trazabilidad);
    Trazabilidad toDomain(TrazabilidadDocument trazabilidadDocument);
}
