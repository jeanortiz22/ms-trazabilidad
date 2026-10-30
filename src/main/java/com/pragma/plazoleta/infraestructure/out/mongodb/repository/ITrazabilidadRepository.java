package com.pragma.plazoleta.infraestructure.out.mongodb.repository;

import com.pragma.plazoleta.infraestructure.out.mongodb.document.TrazabilidadDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ITrazabilidadRepository extends MongoRepository<TrazabilidadDocument, UUID> {
    List<TrazabilidadDocument> findByIdPedidoAndIdClienteOrderByFechaAsc(UUID idPedido, UUID idCliente);
    List<TrazabilidadDocument> findByIdRestauranteAndIdPropietario(UUID idRestaurante, UUID idPropietario);

}
