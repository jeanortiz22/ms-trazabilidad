package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.TrazabilidadNoEncontradaException;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadPersistencePort trazabilidadPersistencePort;

    @InjectMocks
    private TrazabilidadUseCase trazabilidadUseCase;

    private static final UUID ID_PEDIDO = UUID.randomUUID();
    private static final UUID ID_CLIENTE = UUID.randomUUID();

    @Test
    void registrar_asignaLaFechaYGuarda(){
        Trazabilidad trazabilidad = new Trazabilidad();
        trazabilidad.setIdPedido(ID_PEDIDO);

        trazabilidadUseCase.registrar(trazabilidad);

        ArgumentCaptor<Trazabilidad> captor = ArgumentCaptor.forClass(Trazabilidad.class);
        verify(trazabilidadPersistencePort).guardar(captor.capture());
        assertNotNull(captor.getValue().getFecha());
    }

    @Test
    void consultarHistorial_devuelveLosRegistrosDelCliente(){
        List<Trazabilidad> esperados = List.of((new Trazabilidad()));
        when(trazabilidadPersistencePort.buscarPorPedidoYCliente(ID_PEDIDO, ID_CLIENTE))
                .thenReturn(esperados);
        assertSame(esperados, trazabilidadUseCase.consultarHistorial(ID_PEDIDO, ID_CLIENTE));
    }

    @Test
    void consultarHistorial_fallaSiNoHayRegistrosDelCliente(){
        when(trazabilidadPersistencePort.buscarPorPedidoYCliente(ID_PEDIDO, ID_CLIENTE))
                .thenReturn(List.of());
        assertThrows(TrazabilidadNoEncontradaException.class,
                () -> trazabilidadUseCase.consultarHistorial(ID_PEDIDO, ID_CLIENTE));
    }
}
