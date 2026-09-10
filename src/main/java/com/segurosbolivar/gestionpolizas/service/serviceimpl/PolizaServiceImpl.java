package com.segurosbolivar.gestionpolizas.service.serviceimpl;

import com.segurosbolivar.gestionpolizas.dto.request.RiesgoRequestDTO;
import com.segurosbolivar.gestionpolizas.dto.response.PolizaResponseDTO;
import com.segurosbolivar.gestionpolizas.dto.response.RiesgoResponseDTO;
import com.segurosbolivar.gestionpolizas.exception.PolizaCanceladaException;
import com.segurosbolivar.gestionpolizas.exception.PolizaNoEncontradaException;
import com.segurosbolivar.gestionpolizas.exception.ReglaNegocioException;
import com.segurosbolivar.gestionpolizas.mapper.PolizaMapper;
import com.segurosbolivar.gestionpolizas.mapper.RiesgoMapper;
import com.segurosbolivar.gestionpolizas.models.Poliza;
import com.segurosbolivar.gestionpolizas.models.Riesgo;
import com.segurosbolivar.gestionpolizas.models.enums.EstadoPoliza;
import com.segurosbolivar.gestionpolizas.models.enums.EstadoRiesgo;
import com.segurosbolivar.gestionpolizas.models.enums.TipoPoliza;
import com.segurosbolivar.gestionpolizas.repository.PolizaRepository;
import com.segurosbolivar.gestionpolizas.service.CoreNotificationService;
import com.segurosbolivar.gestionpolizas.service.PolizaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolizaServiceImpl implements PolizaService {

    private static final BigDecimal IPC_ACTUAL = new BigDecimal("0.065");

    private final PolizaRepository polizaRepository;
    private final CoreNotificationService coreNotificationService;

    @Override
    public List<PolizaResponseDTO> listar(String tipo, String estado) {
        TipoPoliza tipoEnum = parseTipo(tipo);
        EstadoPoliza estadoEnum = parseEstado(estado);

        List<Poliza> polizas;
        if (tipoEnum != null && estadoEnum != null) {
            polizas = polizaRepository.findByTipoAndEstado(tipoEnum, estadoEnum);
        } else if (tipoEnum != null) {
            polizas = polizaRepository.findByTipo(tipoEnum);
        } else if (estadoEnum != null) {
            polizas = polizaRepository.findByEstado(estadoEnum);
        } else {
            polizas = polizaRepository.findAll();
        }

        return polizas.stream()
                .map(PolizaMapper::toDTO)
                .toList();
    }

    @Override
    public List<RiesgoResponseDTO> listarRiesgos(Long polizaId) {
        Poliza poliza = obtenerPoliza(polizaId);
        return poliza.getRiesgos().stream()
                .map(RiesgoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public PolizaResponseDTO renovar(Long polizaId) {
        Poliza poliza = obtenerPoliza(polizaId);

        if (poliza.estaCancelada()) {
            throw new PolizaCanceladaException(polizaId);
        }

        poliza.renovar(IPC_ACTUAL);
        Poliza actualizada = polizaRepository.save(poliza);

        coreNotificationService.notificarEvento("ACTUALIZACION", polizaId);

        return PolizaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public PolizaResponseDTO cancelar(Long polizaId) {
        Poliza poliza = obtenerPoliza(polizaId);

        if (poliza.estaCancelada()) {
            throw new ReglaNegocioException("La póliza " + polizaId + " ya se encuentra cancelada");
        }

        poliza.cancelarConRiesgos();
        Poliza actualizada = polizaRepository.save(poliza);

        coreNotificationService.notificarEvento("ACTUALIZACION", polizaId);

        return PolizaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public RiesgoResponseDTO agregarRiesgo(Long polizaId, RiesgoRequestDTO request) {
        Poliza poliza = obtenerPoliza(polizaId);

        if (!poliza.esColectiva()) {
            throw new ReglaNegocioException("Solo pólizas de tipo Colectiva pueden agregar riesgos");
        }

        if (poliza.estaCancelada()) {
            throw new PolizaCanceladaException(polizaId);
        }

        Riesgo riesgo = Riesgo.builder()
                .descripcion(request.getDescripcion())
                .direccionInmueble(request.getDireccionInmueble())
                .estado(EstadoRiesgo.ACTIVO)
                .build();

        poliza.agregarRiesgo(riesgo);
        polizaRepository.save(poliza);

        coreNotificationService.notificarEvento("ACTUALIZACION", polizaId);

        return RiesgoMapper.toDTO(riesgo);
    }


    private Poliza obtenerPoliza(Long id) {
        return polizaRepository.findById(id)
                .orElseThrow(() -> new PolizaNoEncontradaException(id));
    }

    private TipoPoliza parseTipo(String tipo) {
        if (StringUtils_hasText(tipo)) return null;
        try {
            return TipoPoliza.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ReglaNegocioException("Tipo de póliza inválido: " + tipo);
        }
    }

    private EstadoPoliza parseEstado(String estado) {
        if (StringUtils_hasText(estado)) return null;
        try {
            return EstadoPoliza.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ReglaNegocioException("Estado de póliza inválido: " + estado);
        }
    }

    private boolean StringUtils_hasText(String s) {
        return s == null || s.isBlank();
    }
}
