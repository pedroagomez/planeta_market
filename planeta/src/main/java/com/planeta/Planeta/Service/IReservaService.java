package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.ReservaDTO;
import com.planeta.Planeta.Model.Reserva;

import java.util.List;

public interface IReservaService {


    void realizarReserva(Reserva reserva);
    List<ReservaDTO>obtenerTodasLasReservas();
    ReservaDTO obtenerReservaPorId(Long id);
    void cancelarReserva(Long id);



}
