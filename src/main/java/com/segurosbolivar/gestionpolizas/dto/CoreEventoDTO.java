package com.segurosbolivar.gestionpolizas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreEventoDTO {

    private String evento;
    private Long polizaId;
}
