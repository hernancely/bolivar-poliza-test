package com.segurosbolivar.gestionpolizas.service;

import com.segurosbolivar.gestionpolizas.dto.request.RiesgoRequestDTO;
import com.segurosbolivar.gestionpolizas.dto.response.PolizaResponseDTO;
import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;

import java.util.List;

public interface PolizaService {
    List<PolizaResponseDTO> listar(String tipo, String estado);

    List<RiesgoResponseDTO> listarRiesgos(Long id);

    PolizaResponseDTO renovar(Long id);

    PolizaResponseDTO cancelar(Long id);

    RiesgoResponseDTO agregarRiesgo(Long id, RiesgoRequestDTO request);
}
