package com.segurosbolivar.gestionpolizas.exceptions;

public class RiesgoNoEncontradoException  extends RuntimeException{
    public RiesgoNoEncontradoException(Long id) {
        super("No se encontró el riesgo con id: " + id);
    }
}
