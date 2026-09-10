package com.segurosbolivar.gestionpolizas.service.serviceimpl;

import com.segurosbolivar.gestionpolizas.exception.RiesgoNoEncontradoException;
import com.segurosbolivar.gestionpolizas.models.Riesgo;
import com.segurosbolivar.gestionpolizas.repository.RiesgoRepository;
import com.segurosbolivar.gestionpolizas.service.CoreNotificationService;
import com.segurosbolivar.gestionpolizas.service.RiesgoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiesgoServiceImpl implements RiesgoService {

    private final RiesgoRepository riesgoRepository;
    private final CoreNotificationService coreNotificationService;

    @Override
    @Transactional
    public void cancelar(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new RiesgoNoEncontradoException(riesgoId));

        riesgo.cancelar();
        riesgoRepository.save(riesgo);

        coreNotificationService.notificarEvento("ACTUALIZACION", riesgo.getPoliza().getId());
    }
}
