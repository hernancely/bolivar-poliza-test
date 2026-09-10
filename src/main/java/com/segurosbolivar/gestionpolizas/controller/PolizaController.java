package com.segurosbolivar.gestionpolizas.controller;

import com.segurosbolivar.gestionpolizas.dto.request.RiesgoRequestDTO;
import com.segurosbolivar.gestionpolizas.dto.response.PolizaResponseDTO;
import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;
import com.segurosbolivar.gestionpolizas.service.PolizaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService polizaService;

    @GetMapping
    public ResponseEntity<List<PolizaResponseDTO>> listar(@RequestParam(required = false) String tipo, @RequestParam(required = false) String estado){
        return ResponseEntity.ok(polizaService.listar(tipo,estado));
    }

    @GetMapping("/{id}/riesgos")
    public ResponseEntity<List<RiesgoResponseDTO>> listarRiesgos(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.listarRiesgos(id));
    }

    @PostMapping("/{id}/renovar")
    public ResponseEntity<PolizaResponseDTO> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.renovar(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PolizaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.cancelar(id));
    }

    @PostMapping("/{id}/riesgos")
    public ResponseEntity<RiesgoResponseDTO> agregarRiesgo(
            @PathVariable Long id,
            @RequestBody @Valid RiesgoRequestDTO request) {
        return ResponseEntity.ok(polizaService.agregarRiesgo(id, request));
    }
}
