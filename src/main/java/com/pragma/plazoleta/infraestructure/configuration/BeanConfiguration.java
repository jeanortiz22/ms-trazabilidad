package com.pragma.plazoleta.infraestructure.configuration;

import com.pragma.plazoleta.application.mapper.TrazabilidadRequestMapper;
import com.pragma.plazoleta.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.plazoleta.domain.usecase.TrazabilidadUseCase;
import com.pragma.plazoleta.infraestructure.out.mongodb.adapter.TrazabilidadMongoAdapter;
import com.pragma.plazoleta.infraestructure.out.mongodb.document.TrazabilidadDocument;
import com.pragma.plazoleta.infraestructure.out.mongodb.mapper.TrazabilidadDocumentMapper;
import com.pragma.plazoleta.infraestructure.out.mongodb.repository.ITrazabilidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITrazabilidadRepository trazabilidadRepository;
    private final TrazabilidadDocumentMapper trazabilidadDocumentMapper;

    @Bean
    public ITrazabilidadPersistencePort trazabilidadPersistencePort () {
        return new TrazabilidadMongoAdapter(trazabilidadRepository, trazabilidadDocumentMapper);
    }

    @Bean
    public ITrazabilidadServicePort trazabilidadServicePort() {
        return new TrazabilidadUseCase(trazabilidadPersistencePort());
    }
}
