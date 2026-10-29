package com.pragma.plazoleta.domain.exception;

public class TrazabilidadNoEncontradaException extends RuntimeException {
    public TrazabilidadNoEncontradaException() {
        super("No se encontro la trazabilidad del pedido solicitado");
    }
}
