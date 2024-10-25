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

    @Override
    public void crearViaje(Viaje viaje) {
        viajeRepository.save(viaje);
    }

    @Override
    public Viaje obtenerViajePorId(Long id) {
        return viajeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id no encontrado"));
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

        // Asegúrate de que el destino no sea nulo antes de mapear
        if (viaje.getDestino() != null) {
            dto.setDestino(mapearPlaneta(viaje.getDestino()));
        } else {
            dto.setDestino(null);
        }

        // Asegúrate de que estos campos se asignen correctamente
        dto.setAsientosDisponibles(viaje.getAsientosDisponibles());
        dto.setCapacidadTotal(viaje.getCapacidadTotal());
        dto.setPrecioPorPasajero(viaje.getPrecioPorPasajero());

        // Mapeo de reservas sin llamar a mapearViajeADTO para evitar recursión
        if (viaje.getReservas() != null) {
            dto.setReservasDto(viaje.getReservas().stream()
                    .map(this::mapearReservaADTOSinViaje)
                    .collect(Collectors.toList()));
        } else {
            dto.setReservasDto(new ArrayList<>()); // Inicializa como una lista vacía si es nulo
        }

        return dto;
    }

    // Método auxiliar para mapear una Reserva a ReservaDTO sin llamar a mapearViajeADTO
    private ReservaDTO mapearReservaADTOSinViaje(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setFechaReserva(reserva.getFechaReserva());

        // Mapeo de pasajeros
        dto.setPasajeros(reserva.getPasajeros().stream()
                .map(pasajero -> mapearPasajeroADTO(pasajero, reserva.getId())) // Pasamos el reservaId
                .collect(Collectors.toList()));
        dto.setPrecioTotal(reserva.getPrecioTotal());

        return dto;
    }

    // Método auxiliar para mapear un Pasajero a PasajeroDTO
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
        // Verifica que el viaje existe antes de actualizar
        if (!viajeRepository.existsById(viaje.getId())) {
            throw new EntityNotFoundException("No se encontró el viaje con ID: " + viaje.getId());
        }
        viajeRepository.save(viaje); // Guarda la entidad actualizada
    }

    @Override
    public void eliminarViaje(Long id) {
        viajeRepository.deleteById(id);
    }
}
