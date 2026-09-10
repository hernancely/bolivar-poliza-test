package com.segurosbolivar.gestionpolizas.service.serviceimpl;

import com.segurosbolivar.gestionpolizas.service.CoreNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoreNotificationServiceImpl implements CoreNotificationService {
    @Override
    public void notificarEvento(String evento, Long polizaId) {
        log.info("Enviando evento al CORE -> evento: {}, polizaId: {}", evento, polizaId);
    }
}
