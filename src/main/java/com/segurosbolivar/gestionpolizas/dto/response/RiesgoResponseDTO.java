package com.segurosbolivar.gestionpolizas.dto.response;

import com.segurosbolivar.gestionpolizas.models.enums.EstadoRiesgo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiesgoResponseDTO {

    private Long id;
    private String descripcion;
    private String direccionInmueble;
    private EstadoRiesgo estado;
    private Long polizaId;
}
