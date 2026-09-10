package com.segurosbolivar.gestionpolizas.exception;

public class PolizaCanceladaException extends RuntimeException{
    public PolizaCanceladaException(Long id) {
        super("La póliza " + id + " está cancelada y no admite esta operación");
    }
}
