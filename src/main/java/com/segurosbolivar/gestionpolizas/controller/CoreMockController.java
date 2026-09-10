package com.segurosbolivar.gestionpolizas.controller;


import com.segurosbolivar.gestionpolizas.dto.CoreEventoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    @PostMapping("/evento")
    public ResponseEntity<Void> registrarEvento(@RequestBody CoreEventoDTO evento) {
        log.info("Operación intentada enviar al CORE -> evento: {}, polizaId: {}",
                evento.getEvento(), evento.getPolizaId());
        return ResponseEntity.ok().build();
    }
}
