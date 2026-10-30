package com.pragma.plazoleta.infraestructure.out.mongodb.adapter;

import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.plazoleta.infraestructure.out.mongodb.mapper.TrazabilidadDocumentMapper;
import com.pragma.plazoleta.infraestructure.out.mongodb.repository.ITrazabilidadRepository;

import java.util.List;
import java.util.UUID;

public class TrazabilidadMongoAdapter implements ITrazabilidadPersistencePort {

    private final ITrazabilidadRepository trazabilidadRepository;
    private final TrazabilidadDocumentMapper trazabilidadDocumentMapper;

    public TrazabilidadMongoAdapter(ITrazabilidadRepository trazabilidadRepository, TrazabilidadDocumentMapper trazabilidadDocumentMapper) {
        this.trazabilidadRepository = trazabilidadRepository;
        this.trazabilidadDocumentMapper = trazabilidadDocumentMapper;
    }

    @Override
    public Trazabilidad guardar(Trazabilidad trazabilidad) {
        return trazabilidadDocumentMapper.toDomain(trazabilidadRepository.save(trazabilidadDocumentMapper.toDocument(trazabilidad)));
    }

    @Override
    public List<Trazabilidad> buscarPorPedidoYCliente(UUID idPedido, UUID idCliente) {
        return trazabilidadRepository.findByIdPedidoAndIdClienteOrderByFechaAsc(idPedido, idCliente)
                .stream()
                .map(trazabilidadDocumentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Trazabilidad> buscarPorRestauranteYPropietario(UUID idRestaurante, UUID idPropietario) {
        return trazabilidadRepository.findByIdRestauranteAndIdPropietario(idRestaurante, idPropietario)
                .stream().map(trazabilidadDocumentMapper::toDomain).toList();
    }

}
