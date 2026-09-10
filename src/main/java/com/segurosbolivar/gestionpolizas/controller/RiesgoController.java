package com.segurosbolivar.gestionpolizas.controller;


import com.segurosbolivar.gestionpolizas.service.PolizaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riesgos")
@RequiredArgsConstructor
public class RiesgoController {

    private final PolizaService polizaService;

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        polizaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
