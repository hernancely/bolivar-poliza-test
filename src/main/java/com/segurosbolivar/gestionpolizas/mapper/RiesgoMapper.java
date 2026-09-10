package com.segurosbolivar.gestionpolizas.mapper;

import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;
import com.segurosbolivar.gestionpolizas.models.Riesgo;

public class RiesgoMapper {
    public static RiesgoResponseDTO toDTO(Riesgo r) {
        return RiesgoResponseDTO.builder()
                .id(r.getId())
                .descripcion(r.getDescripcion())
                .direccionInmueble(r.getDireccionInmueble())
                .estado(r.getEstado())
                .polizaId(r.getPoliza().getId())
                .build();
    }
}
