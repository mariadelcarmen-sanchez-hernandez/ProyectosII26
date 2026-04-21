package com.generaction.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.generaction.backend.dto.ValoracionDTO;
import com.generaction.backend.entity.Valoracion;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.repository.ValoracionRepository;
import com.generaction.backend.repository.VisitaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final VisitaRepository visitaRepository;

    public Valoracion crearValoracion(ValoracionDTO dto) {
        Visita visita = visitaRepository.findById(dto.getIdVisita())
                .orElseThrow(() -> new RuntimeException("Visita no encontrada: " + dto.getIdVisita()));

        if (dto.getPuntuacion() == null || dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new RuntimeException("La puntuación debe estar entre 1 y 5.");
        }

        String rolTexto = dto.getRol() == null ? "" : dto.getRol().trim().toUpperCase();

        Valoracion.RolValoracion rol;
        try {
            rol = Valoracion.RolValoracion.valueOf(rolTexto);
        } catch (Exception e) {
            throw new RuntimeException("Rol no válido. Debe ser MAYOR o VOLUNTARIO.");
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setVisita(visita);
        valoracion.setRol(rol);
        valoracion.setPuntuacion(dto.getPuntuacion());
        valoracion.setComentario(dto.getComentario());

        return valoracionRepository.save(valoracion);
    }

    public List<Valoracion> obtenerPorVisita(Long idVisita) {
        return valoracionRepository.findByVisita_IdVisita(idVisita);
    }

    public List<Valoracion> obtenerTodas() {
        return valoracionRepository.findAll();
    }
}