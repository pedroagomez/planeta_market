package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.DTO.ReservaDTO;
import com.planeta.Planeta.DTO.ViajeDTO;
import com.planeta.Planeta.Model.Cliente;
import com.planeta.Planeta.Model.Pasajero;
import com.planeta.Planeta.Model.Reserva;
import com.planeta.Planeta.Model.Viaje;
import com.planeta.Planeta.Service.IClienteService;
import com.planeta.Planeta.Service.IReservaService;
import com.planeta.Planeta.Service.IViajeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IViajeService viajeService;

    @GetMapping("/traer")
    public ResponseEntity<List<ReservaDTO>> obtenerReservas() {
        List<ReservaDTO> reservas = reservaService.obtenerTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Long id) {
        ReservaDTO reserva = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        try {
            reservaService.realizarReserva(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("mensaje", "Reserva creada exitosamente"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear la reserva: " + e.getMessage()));
        }
    }


    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }



    /*
    private Pasajero mapearPasajero(PasajeroDTO pasajeroDTO) {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre(pasajeroDTO.getNombre());
        pasajero.setApellido(pasajeroDTO.getApellido());
        pasajero.setEmail(pasajeroDTO.getEmail());
        return pasajero;
    }

    private Viaje mapearViaje(ViajeDTO viajeDTO) {
        Viaje viaje = new Viaje();
        viaje.setId(viajeDTO.getId());
        viaje.setFechaViaje(viajeDTO.getFechaSalida());
        viaje.setAsientosDisponibles(viajeDTO.getAsientosDisponibles());
        viaje.setCapacidadTotal(viajeDTO.getCapacidadTotal());
        viaje.setPrecioPorPasajero(viajeDTO.getPrecioPorPasajero());
        return viaje;
    }

    private double calcularPrecioTotal(Viaje viaje, int cantidadPasajeros) {
        return viaje.getPrecioPorPasajero() * cantidadPasajeros;
    }*/
}

