package com.segurosbolivar.gestionpolizas.exception;

public class RiesgoNoEncontradoException  extends RuntimeException{
    public RiesgoNoEncontradoException(Long id) {
        super("No se encontró el riesgo con id: " + id);
    }
}
