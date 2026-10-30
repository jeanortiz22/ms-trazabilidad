package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.EficienciaPedidoResponse;
import com.pragma.plazoleta.application.dto.response.RankingEmpleadoResponse;
import com.pragma.plazoleta.application.dto.response.ReporteEficienciaResponse;
import com.pragma.plazoleta.domain.model.EficienciaPedido;
import com.pragma.plazoleta.domain.model.RankingEmpleado;
import com.pragma.plazoleta.domain.model.ReporteEficiencia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EficienciaResponseMapper {
    ReporteEficienciaResponse toResponse(ReporteEficiencia reporte);
    EficienciaPedidoResponse toResponse(EficienciaPedido pedido);
    RankingEmpleadoResponse toResponse(RankingEmpleado ranking);
}
