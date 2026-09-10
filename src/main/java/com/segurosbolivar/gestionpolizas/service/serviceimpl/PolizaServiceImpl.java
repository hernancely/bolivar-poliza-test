package com.segurosbolivar.gestionpolizas.service.serviceimpl;

import com.segurosbolivar.gestionpolizas.dto.request.RiesgoRequestDTO;
import com.segurosbolivar.gestionpolizas.dto.response.PolizaResponseDTO;
import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;
import com.segurosbolivar.gestionpolizas.service.PolizaService;

import java.util.List;

public class PolizaServiceImpl implements PolizaService {
    @Override
    public List<PolizaResponseDTO> listar(String tipo, String estado) {
        return List.of();
    }

    @Override
    public List<RiesgoResponseDTO> listarRiesgos(Long id) {
        return List.of();
    }

    @Override
    public PolizaResponseDTO renovar(Long id) {
        return null;
    }

    @Override
    public PolizaResponseDTO cancelar(Long id) {
        return null;
    }

    @Override
    public RiesgoResponseDTO agregarRiesgo(Long id, RiesgoRequestDTO request) {
        return null;
    }
}
