package com.planeta.Planeta.Service;


import com.planeta.Planeta.DTO.PlanetaDTO;
import com.planeta.Planeta.Model.Planeta;
import com.planeta.Planeta.Repository.IPlanetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlanetaService implements IPlanetaService {
    @Autowired
    IPlanetaRepository planetaService;
    @Override
    public void createPlaneta(Planeta planeta) {
        planetaService.save(planeta);
    }

    @Override
    public PlanetaDTO obtenerPlanetaPorId(Long id) {
        Planeta planeta = planetaService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + id));

        return new PlanetaDTO(planeta.getId(), planeta.getNombre(),planeta.getTipo(), (int) planeta.getKmCuadrados());
    }
    @Override
    public List<PlanetaDTO> obtenerPlanetas() {
        List<Planeta> planetas = planetaService.findAll();


        return planetas.stream()
                .map(planeta -> new PlanetaDTO(planeta.getId(), planeta.getNombre(), planeta.getTipo(), (int) planeta.getKmCuadrados()))
                .collect(Collectors.toList());
    }


    @Override
    public void actualizarPlaneta(Planeta planeta) {
        if (!planetaService.existsById(planeta.getId())) {
            throw new EntityNotFoundException("No se puede actualizar. Cliente no encontrado con id: " + planeta.getId());
        }
        planetaService.save(planeta);
    }

    @Override
    public void eliminarPlaneta(Long id) {
        if (!planetaService.existsById(id)) {
            throw new EntityNotFoundException("Cliente no encontrado con id: " + id);
        }
        planetaService.deleteById(id);
    }
}
