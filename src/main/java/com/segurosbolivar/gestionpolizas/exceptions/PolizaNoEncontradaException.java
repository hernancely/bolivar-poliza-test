package com.segurosbolivar.gestionpolizas.exceptions;

public class PolizaNoEncontradaException extends RuntimeException {
    public PolizaNoEncontradaException(Long id) {
        super("No se encontró la póliza con id: " + id);
    }
}
