package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.Model.Pasajero;

import java.util.List;

public interface IPasajeroService {

    void crearPasajero(Pasajero pasajero);

    PasajeroDTO obtenerPasajeroPorId(Long id);
    List<PasajeroDTO>ObtenerListaPasajeros();

    void actualizarPasajero(Long id, PasajeroDTO pasajeroDTO);

    void eliminarPasajero(Long id);

}
