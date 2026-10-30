package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.TrazabilidadNoEncontradaException;
import com.pragma.plazoleta.domain.model.EficienciaPedido;
import com.pragma.plazoleta.domain.model.ReporteEficiencia;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private static final UUID ID_RESTAURANTE = UUID.randomUUID();
    private static final UUID ID_PROPIETARIO = UUID.randomUUID();
    private static final LocalDateTime T0 = LocalDateTime.of(2024, 10, 20, 12, 0);

    private Trazabilidad evento(UUID idPedido, String estadoNuevo, UUID idEmpleado, LocalDateTime fecha) {
        Trazabilidad t = new Trazabilidad();
        t.setIdPedido(idPedido);
        t.setEstadoNuevo(estadoNuevo);
        t.setIdEmpleado(idEmpleado);
        t.setFecha(fecha);
        return t;
    }

    private List<Trazabilidad> pedidoEntregado(UUID idEmpleado, Long segundos) {
        UUID idPedido = UUID.randomUUID();
        return List.of(
                evento(idPedido, "PENDIENTE", null, T0),
                evento(idPedido, "EN_PREPARACION", idEmpleado, T0.plusSeconds(10)),
                evento(idPedido, "LISTO", idEmpleado, T0.plusSeconds(segundos / 2)),
                evento(idPedido, "ENTREGADO", idEmpleado, T0.plusSeconds(segundos))
        );
    }

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

    @Test
    void calcularEficiencia_calculaTiempoPorPedidoYRankingPorEmpleado() {
        UUID emp1 = UUID.randomUUID();
        UUID emp2 = UUID.randomUUID();
        List<Trazabilidad> registros = new ArrayList<>();
        registros.addAll(pedidoEntregado(emp1, 600L));
        registros.addAll(pedidoEntregado(emp1, 200L));
        registros.addAll(pedidoEntregado(emp2, 100L));
        when(trazabilidadPersistencePort.buscarPorRestauranteYPropietario(ID_RESTAURANTE, ID_PROPIETARIO)).thenReturn(registros);

        ReporteEficiencia reporte = trazabilidadUseCase.calcularEficiencia(ID_RESTAURANTE, ID_PROPIETARIO);

        assertEquals(3, reporte.pedidos().size());
        assertEquals(List.of(100L, 200L, 600L),
                reporte.pedidos().stream().map(EficienciaPedido::duracionSegundos).sorted().toList());
        assertEquals(2, reporte.ranking().size());
        assertEquals(emp2, reporte.ranking().get(0).idEmpleado());
        assertEquals(100.0, reporte.ranking().get(0).tiempoMedioSegundos());
        assertEquals(emp1, reporte.ranking().get(1).idEmpleado());
        assertEquals(400.0, reporte.ranking().get(1).tiempoMedioSegundos());
        assertEquals(2, reporte.ranking().get(1).pedidosEntregados());
    }

    @Test
    void calcularEficiencia_ignoraPedidosCanceladosYEnCurso() {
        UUID cancelado = UUID.randomUUID();
        UUID enCurso = UUID.randomUUID();
        UUID empleado = UUID.randomUUID();
        when(trazabilidadPersistencePort.buscarPorRestauranteYPropietario(ID_RESTAURANTE, ID_PROPIETARIO)).thenReturn(List.of(
                evento(cancelado, "PENDIENTE", null, T0),
                evento(cancelado, "CANCELADO", null, T0.plusSeconds(30)),
                evento(enCurso, "PENDIENTE", null, T0),
                evento(enCurso, "EN_PREPARACION", empleado, T0.plusSeconds(20))));

        ReporteEficiencia reporte = trazabilidadUseCase.calcularEficiencia(ID_RESTAURANTE, ID_PROPIETARIO);

        assertTrue(reporte.pedidos().isEmpty());
        assertTrue(reporte.ranking().isEmpty());
    }

    @Test
    void calcularEficiencia_sinRegitrosDevuelveReporteVacio(){
        when(trazabilidadPersistencePort.buscarPorRestauranteYPropietario(ID_RESTAURANTE, ID_PROPIETARIO)).thenReturn(List.of());

        ReporteEficiencia reporte = trazabilidadUseCase.calcularEficiencia(ID_RESTAURANTE, ID_PROPIETARIO);

        assertTrue(reporte.pedidos().isEmpty());
        assertTrue(reporte.ranking().isEmpty());
    }
}
