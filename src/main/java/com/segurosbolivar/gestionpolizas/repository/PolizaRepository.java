package com.segurosbolivar.gestionpolizas.repository;

import com.segurosbolivar.gestionpolizas.models.Poliza;
import com.segurosbolivar.gestionpolizas.models.enums.EstadoPoliza;
import com.segurosbolivar.gestionpolizas.models.enums.TipoPoliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza,Long> {

    List<Poliza> findByTipoAndEstado(TipoPoliza tipo, EstadoPoliza estado);

    List<Poliza> findByTipo(TipoPoliza tipo);

    List<Poliza> findBYEstado(EstadoPoliza estado);


}
