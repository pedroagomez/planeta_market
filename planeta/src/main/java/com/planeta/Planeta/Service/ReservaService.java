package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.DTO.PlanetaDTO;
import com.planeta.Planeta.DTO.ReservaDTO;
import com.planeta.Planeta.DTO.ViajeDTO;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Repository.IClienteRepository;
import com.planeta.Planeta.Repository.IPasajeroRepository;
import com.planeta.Planeta.Repository.IReservaRepository;
import com.planeta.Planeta.Repository.IViajeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;

@Service
public class ReservaService implements IReservaService {
    @Autowired
    private IReservaRepository reservaRepo;
    @Autowired
    private IViajeRepository viajeRepository;
    @Autowired
    private IPasajeroRepository pasajeroRepository;
    @Autowired
    private IClienteRepository clienteRepository;

    @Transactional
    @Override
    public void realizarReserva(ReservaDTO reservaDTO) {
        // Obtener el cliente
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        // Obtener el viaje
        Viaje viaje = viajeRepository.findById(reservaDTO.getViaje().getId())
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado"));

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setViaje(viaje);
        reserva.setFechaReserva(LocalDate.now());
        reserva.setPasajeros(new ArrayList<>());


        for (PasajeroDTO pasajeroDTO : reservaDTO.getPasajeros()) {
            Pasajero pasajero;

            if (pasajeroDTO.getId() != null) {
                pasajero = pasajeroRepository.findById(pasajeroDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Pasajero no encontrado con ID: " + pasajeroDTO.getId()));
            } else {

                pasajero = new Pasajero();
                pasajero.setNombre(pasajeroDTO.getNombre());
                pasajero.setApellido(pasajeroDTO.getApellido());
                pasajero.setEmail(pasajeroDTO.getEmail());
            }

            pasajero.setReserva(reserva);
            reserva.getPasajeros().add(pasajero);
        }

        validarReserva(reserva);

        double precioTotal = viaje.getPrecioPorPasajero() * reserva.getPasajeros().size();
        reserva.setPrecioTotal(precioTotal);


        actualizarViaje(viaje, reserva.getPasajeros().size());

        reservaRepo.save(reserva);

        cliente.getReservas().add(reserva);
        clienteRepository.save(cliente);
    }

    private void validarReserva(Reserva reserva) {
        Viaje viaje = reserva.getViaje();

        if (viaje == null) {
            throw new IllegalArgumentException("El viaje no está disponible.");
        }

        if (reserva.getPasajeros() == null || reserva.getPasajeros().isEmpty()) {
            throw new IllegalArgumentException("La reserva debe tener al menos un pasajero.");
        }

        Integer asientosDisponibles = viaje.getAsientosDisponibles();
        if (asientosDisponibles == null || asientosDisponibles <= 0) {
            throw new IllegalArgumentException("El viaje no tiene asientos disponibles.");
        }

        if (asientosDisponibles < reserva.getPasajeros().size()) {
            throw new IllegalArgumentException("No hay suficientes asientos disponibles para la reserva.");
        }
    }

    private void actualizarViaje(Viaje viaje, int cantidadPasajeros) {
        viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - cantidadPasajeros);
        viajeRepository.save(viaje);
    }

    @Override
    public List<ReservaDTO> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaRepo.findAll();
        return reservas.stream()
                .map(this::mapearReservaADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));
        return mapearReservaADTO(reserva);
    }

    private ReservaDTO mapearReservaADTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setViaje(mapearViajeADTO(reserva.getViaje()));
        dto.setFechaReserva(reserva.getFechaReserva());

        // Mapea los pasajeros
        if (reserva.getPasajeros() != null) {
            dto.setPasajeros(reserva.getPasajeros().stream()
                    .map(this::mapearPasajeroADTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setPasajeros(Collections.emptyList());
        }

        dto.setPrecioTotal(reserva.getPrecioTotal());
        return dto;
    }

    private PasajeroDTO mapearPasajeroADTO(Pasajero pasajero) {
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(pasajero.getId());
        dto.setNombre(pasajero.getNombre());
        dto.setApellido(pasajero.getApellido());
        dto.setEmail(pasajero.getEmail());
        return dto;
    }

    private ViajeDTO mapearViajeADTO(Viaje viaje) {
        if (viaje == null) {
            return null;
        }

        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setFechaSalida(viaje.getFechaViaje());
        dto.setDestino(mapearPlanetaADTO(viaje.getDestino()));
        dto.setAsientosDisponibles(viaje.getAsientosDisponibles());
        dto.setCapacidadTotal(viaje.getCapacidadTotal());
        dto.setPrecioPorPasajero(viaje.getPrecioPorPasajero());
        return dto;
    }

    private PlanetaDTO mapearPlanetaADTO(Planeta planeta) {
        if (planeta == null) {
            return null;
        }

        PlanetaDTO dto = new PlanetaDTO();
        dto.setId(planeta.getId());
        dto.setNombre(planeta.getNombre());
        dto.setTipo(planeta.getTipo());
        dto.setKmCuadrados(planeta.getKmCuadrados());
        return dto;
    }

    @Override
    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));

        liberarAsientos(reserva);


        reservaRepo.delete(reserva);
    }

    private void liberarAsientos(Reserva reserva) {
        Viaje viaje = reserva.getViaje();
        viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() + reserva.getPasajeros().size());
        viajeRepository.save(viaje);
    }
}