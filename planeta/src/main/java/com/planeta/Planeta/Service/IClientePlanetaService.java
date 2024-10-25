package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.ClientePlanetaPropiedadDTO;
import com.planeta.Planeta.Model.ClientePlanetaPropiedad;

import java.util.List;

public interface IClientePlanetaService {


    void crearClientePlaneta(ClientePlanetaPropiedad clientePlanetaPropiedad);

    ClientePlanetaPropiedadDTO obtenerClientePlanetaPorId(Long id);

    List<ClientePlanetaPropiedadDTO> obtenerTodosClientesPlanetas();

    void actualizarClientePlaneta(Long id, ClientePlanetaPropiedad clientePlanetaPropiedad);

    void eliminarClientePlaneta(Long id);


}
