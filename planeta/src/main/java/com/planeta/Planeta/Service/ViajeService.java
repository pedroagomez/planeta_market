package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.DTO.PlanetaDTO;
import com.planeta.Planeta.DTO.ReservaDTO;
import com.planeta.Planeta.DTO.ViajeDTO;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Repository.IClienteRepository;
import com.planeta.Planeta.Repository.IPasajeroRepository;
import com.planeta.Planeta.Repository.IPlanetaRepository;
import com.planeta.Planeta.Repository.IViajeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViajeService implements IViajeService {

    @Autowired
    private IViajeRepository viajeRepository;
    @Autowired
    private IPlanetaRepository planetaRepository;

    public void crearViaje(Viaje viaje) {

        if (viaje.getDestino() == null || !planetaRepository.existsById(viaje.getDestino().getId())) {
            throw new IllegalArgumentException("El planeta especificado no existe");
        }
        viajeRepository.save(viaje);
    }

    @Override
    public ViajeDTO obtenerViajePorId(Long id) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id no encontrado"));
        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setFechaSalida(viaje.getFechaViaje());
        dto.setDestino(mapearPlaneta(viaje.getDestino()));
        dto.setAsientosDisponibles(viaje.getAsientosDisponibles());
        dto.setCapacidadTotal(viaje.getCapacidadTotal());
        dto.setPrecioPorPasajero(viaje.getPrecioPorPasajero());
        return dto;
    }

    private PlanetaDTO mapearPlaneta(Planeta planeta) {
        return new PlanetaDTO(
                planeta.getId(),
                planeta.getNombre(),
                planeta.getTipo(),
                planeta.getKmCuadrados()
        );
    }

    @Override
    public List<ViajeDTO> obtenerTodosLosViajes() {
        List<Viaje> viajes = viajeRepository.findAll();

        return viajes.stream()
                .filter(viaje -> viaje.getFechaViaje() != null &&
                        viaje.getCapacidadTotal() != null &&
                        viaje.getAsientosDisponibles() != null &&
                        viaje.getPrecioPorPasajero() != null) // Filtra viajes válidos
                .map(this::mapearViajeADTO)
                .collect(Collectors.toList());
    }

    private ViajeDTO mapearViajeADTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setFechaSalida(viaje.getFechaViaje());

        if (viaje.getDestino() != null) {
            dto.setDestino(mapearPlaneta(viaje.getDestino()));
        } else {
            dto.setDestino(null);
        }

        dto.setAsientosDisponibles(viaje.getAsientosDisponibles());
        dto.setCapacidadTotal(viaje.getCapacidadTotal());
        dto.setPrecioPorPasajero(viaje.getPrecioPorPasajero());

       /* if (viaje.getReservas() != null) {
            dto.setReservasDto(viaje.getReservas().stream()
                    .map(this::mapearReservaADTOSinViaje)
                    .collect(Collectors.toList()));
        } else {
            dto.setReservasDto(new ArrayList<>());
        }
        */
        return dto;
    }


    private ReservaDTO mapearReservaADTOSinViaje(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setFechaReserva(reserva.getFechaReserva());

        // Mapeo de pasajeros
        dto.setPasajeros(reserva.getPasajeros().stream()
                .map(pasajero -> mapearPasajeroADTO(pasajero, reserva.getId()))
                .collect(Collectors.toList()));
        dto.setPrecioTotal(reserva.getPrecioTotal());

        return dto;
    }


    private PasajeroDTO mapearPasajeroADTO(Pasajero pasajero, Long reservaId) {
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(pasajero.getId());
        dto.setNombre(pasajero.getNombre());
        dto.setApellido(pasajero.getApellido());
        dto.setEmail(pasajero.getEmail());
        dto.setReservaId(reservaId);

        return dto;
    }

    @Override
    public void actualizarViaje(Viaje viaje) {
        if (!viajeRepository.existsById(viaje.getId())) {
            throw new EntityNotFoundException("No se encontró el viaje con ID: " + viaje.getId());
        }
        viajeRepository.save(viaje);
    }

    @Override
    public void eliminarViaje(Long id) {
        viajeRepository.deleteById(id);
    }
}
