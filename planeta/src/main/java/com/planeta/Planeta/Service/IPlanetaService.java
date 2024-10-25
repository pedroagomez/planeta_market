package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.PlanetaDTO;
import com.planeta.Planeta.Model.Planeta;

import java.util.List;

public interface IPlanetaService {

    void createPlaneta(Planeta planeta);

    PlanetaDTO obtenerPlanetaPorId(Long id);
    List<PlanetaDTO> obtenerPlanetas();

    void actualizarPlaneta(Planeta planeta);

    void eliminarPlaneta(Long id);


}
