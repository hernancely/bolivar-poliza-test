package com.segurosbolivar.gestionpolizas.service;

public interface CoreNotificationService {
    void notificarEvento(String actualizacion, Long polizaId);
}
