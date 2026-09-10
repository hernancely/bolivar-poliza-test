package com.segurosbolivar.gestionpolizas.exception;

public class PolizaNoEncontradaException extends RuntimeException {
    public PolizaNoEncontradaException(Long id) {
        super("No se encontró la póliza con id: " + id);
    }
}
