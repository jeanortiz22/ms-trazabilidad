package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoleta.domain.exception.TrazabilidadNoEncontradaException;
import com.pragma.plazoleta.domain.model.EficienciaPedido;
import com.pragma.plazoleta.domain.model.RankingEmpleado;
import com.pragma.plazoleta.domain.model.ReporteEficiencia;
import com.pragma.plazoleta.domain.model.Trazabilidad;
import com.pragma.plazoleta.domain.spi.ITrazabilidadPersistencePort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort trazabilidadPersistencePort;
    private static final String ESTADO_INICIO = "PENDIENTE";
    private static final String ESTADO_ASIGNADO= "EN_PREPARACION";
    private static final String ESTADO_FIN = "ENTREGADO";

    public TrazabilidadUseCase(ITrazabilidadPersistencePort trazabilidadPersistencePort) {
        this.trazabilidadPersistencePort = trazabilidadPersistencePort;
    }

    @Override
    public void registrar(Trazabilidad trazabilidad) {
        trazabilidad.setFecha(LocalDateTime.now());
        trazabilidadPersistencePort.guardar(trazabilidad);
    }

    @Override
    public List<Trazabilidad> consultarHistorial(UUID idPedido, UUID idCliente) {
        List<Trazabilidad> historial = trazabilidadPersistencePort.buscarPorPedidoYCliente(idPedido, idCliente);
        if (historial.isEmpty()) {
            throw new TrazabilidadNoEncontradaException();
        }
        return historial;
    }

    @Override
    public ReporteEficiencia calcularEficiencia(UUID idRestaurante, UUID idPropietario) {
        List<Trazabilidad> registros =
                trazabilidadPersistencePort.buscarPorRestauranteYPropietario(idRestaurante, idPropietario);

        List<EficienciaPedido> pedidos = registros.stream()
                .collect(Collectors.groupingBy(Trazabilidad::getIdPedido))
                .values().stream()
                .map(this::construirEficiencia)
                .flatMap(Optional::stream)
                .sorted(Comparator.comparing(EficienciaPedido::fin))
                .toList();

        List<RankingEmpleado> ranking = pedidos.stream()
                .collect(Collectors.groupingBy(EficienciaPedido::idEmpleado))
                .entrySet().stream()
                .map(entrada-> new RankingEmpleado(
                        entrada.getKey(),
                        entrada.getValue().stream()
                                .mapToLong(EficienciaPedido::duracionSegundos)
                                .average()
                                .orElse(0),
                        entrada.getValue().size()))
                .sorted(Comparator.comparing(RankingEmpleado::tiempoMedioSegundos))
                .toList();

        return new ReporteEficiencia(pedidos, ranking);
    }

    private Optional<EficienciaPedido> construirEficiencia(List<Trazabilidad> eventos) {
        Optional<Trazabilidad> inicio = primero(eventos, ESTADO_INICIO);
        Optional<Trazabilidad> asignacion = primero(eventos, ESTADO_ASIGNADO);
        Optional<Trazabilidad> fin = primero(eventos, ESTADO_FIN);
        if (inicio.isEmpty() || asignacion.isEmpty() || fin.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new EficienciaPedido(
                inicio.get().getIdPedido(),
                asignacion.get().getIdEmpleado(),
                inicio.get().getFecha(),
                fin.get().getFecha(),
                Duration.between(inicio.get().getFecha(), fin.get().getFecha()).getSeconds()
        ));
    }

    private Optional<Trazabilidad> primero(List<Trazabilidad> eventos, String estadoNuevo) {
        return eventos.stream()
                .filter(e -> estadoNuevo.equals(e.getEstadoNuevo()))
                .findFirst();
    }
}
