package com.segurosbolivar.gestionpolizas.repository;

import com.segurosbolivar.gestionpolizas.models.Riesgo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {

    List<Riesgo> findByPolizaId(Long polizaId);
}
