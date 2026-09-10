package com.segurosbolivar.gestionpolizas.mapper;

import com.segurosbolivar.gestionpolizas.dto.response.PolizaResponseDTO;
import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;
import com.segurosbolivar.gestionpolizas.models.Poliza;
import com.segurosbolivar.gestionpolizas.models.Riesgo;

public class PolizaMapper {

    public static PolizaResponseDTO toDTO(Poliza p) {
        return PolizaResponseDTO.builder()
                .id(p.getId())
                .tipo(p.getTipo())
                .estado(p.getEstado())
                .fechaInicioVigencia(p.getFechaInicioVigencia())
                .fechaFinVigencia(p.getFechaFinVigencia())
                .numeroMeses(p.getNumeroMeses())
                .valorCanon(p.getValorCanon())
                .valorPrima(p.getValorPrima())
                .arrendatario(p.getArrendatario())
                .arrendador(p.getArrendador())
                .build();
    }


}
