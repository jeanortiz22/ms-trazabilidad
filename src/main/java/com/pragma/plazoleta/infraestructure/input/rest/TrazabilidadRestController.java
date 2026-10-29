package com.pragma.plazoleta.infraestructure.input.rest;

import com.pragma.plazoleta.application.dto.request.RegistrarTrazabilidadRequest;
import com.pragma.plazoleta.application.dto.response.TrazabilidadResponse;
import com.pragma.plazoleta.application.handler.ITrazabilidadHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trazabilidad")
@RequiredArgsConstructor
public class TrazabilidadRestController {

    private final ITrazabilidadHandler trazabilidadHandler;

    @PreAuthorize("hasAnyRole('CLIENTE', 'EMPLEADO')")
    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody RegistrarTrazabilidadRequest request) {
        trazabilidadHandler.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping("/pedidos/{idPedido}")
    public ResponseEntity<List<TrazabilidadResponse>> consultarHistorial(@PathVariable UUID idPedido,
                                                                         Authentication authentication) {
        UUID idCliente = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(trazabilidadHandler.consultarHistorial(idPedido, idCliente));
    }
}
