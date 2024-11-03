package com.planeta.Planeta.Controller;


import com.planeta.Planeta.DTO.*;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorController {

    @Autowired
    private AdminService administradorService;

    // === Endpoints para Administradores ===
    @PostMapping("/crear")
    public ResponseEntity<?> crearAdmin(@Valid @RequestBody AdministradorDTO administrador) {
        try {
            administradorService.agregarNuevoAdministrador(administrador);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear admin: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login acceso) {
        try {
            Administrador admin = administradorService.verificarCredenciales(acceso.getEmail(), acceso.getPassword());
            return ResponseEntity.ok(admin);
        } catch (IllegalArgumentException e) {
            // Manejar tanto las credenciales incorrectas como el caso en que el admin ya está conectado
            String mensaje = e.getMessage(); // Capturamos el mensaje de la excepción
            if ("Credenciales incorrectas".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("mensaje", mensaje));
            } else if ("Administrador ya conectado".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("mensaje", mensaje));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al iniciar sesión: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al iniciar sesión: " + e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Login acceso) {
        try {
            Administrador admin = administradorService.findByMail(acceso.getEmail());

            if (admin != null) {
                if (admin.isLoggedIn()) { // Verificar si el administrador está conectado
                    // Cambiar el estado de isLoggedIn a false
                    admin.setLoggedIn(false); // Asegúrate de que tengas el método setLoggedIn en tu entidad Administrador
                    administradorService.guardar(admin); // Guardar el estado actualizado

                    return ResponseEntity.ok(Collections.singletonMap("mensaje", "Administrador desconectado: " + admin.getMail()));
                } else {
                    // El administrador ya estaba desconectado
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Collections.singletonMap("mensaje", "No hay usuarios conectados"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("mensaje", "Administrador no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al cerrar sesión: " + e.getMessage()));
        }
    }



    @GetMapping("/traer")
    public ResponseEntity<List<AdministradorDTO>> obtenerAdmin() {
        try {
            List<AdministradorDTO> admin = administradorService.obtenerTodosLosAdministradores();
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((List<AdministradorDTO>) Collections.singletonMap("mensaje", "Error al obtener admin: " + e.getMessage()));
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerAdminId(@PathVariable Long id) {
        try {
            AdministradorDTO admin = administradorService.obtenerAdministradorPorId(id);
            return ResponseEntity.ok(admin);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Admin no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener admin: " + e.getMessage()));
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarAdmin(@PathVariable Long id) {
        try {
            administradorService.eliminarAdministrador(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Admin no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar admin: " + e.getMessage()));
        }
    }


    // === Endpoints para Clientes ===
    @GetMapping("/clientes/traer")
    public ResponseEntity<List<ClienteDTO>> obtenerTodosLosClientes() {
        return ResponseEntity.ok(administradorService.obtenerTodosLosClientes());
    }

    @GetMapping("/clientes/traer/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerClientePorId(id));
    }

    @PutMapping("/clientes/actualizar")
    public ResponseEntity<?> actualizarCliente(@RequestBody Cliente cliente) {
        administradorService.actualizarCliente(cliente);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Cliente actualizado correctamente"));
    }

    @DeleteMapping("/clientes/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        administradorService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // === Endpoints para Planetas ===
    @GetMapping("/planetas/traer")
    public ResponseEntity<List<PlanetaDTO>> obtenerTodosLosPlanetas() {
        return ResponseEntity.ok(administradorService.obtenerTodosLosPlanetas());
    }

    @GetMapping("/planetas/traer/{id}")
    public ResponseEntity<PlanetaDTO> obtenerPlanetaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerPlanetaPorId(id));
    }

    @PostMapping("/planetas/crear")
    public ResponseEntity<?> agregarNuevoPlaneta(@RequestBody Planeta planeta) {
        administradorService.agregarNuevoPlaneta(planeta);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/planetas/actualizar")
    public ResponseEntity<?> actualizarPlaneta(@RequestBody Planeta planeta) {
        administradorService.actualizarPlaneta(planeta);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Planeta actualizado correctamente"));
    }

    @DeleteMapping("/planetas/eliminar/{id}")
    public ResponseEntity<?> eliminarPlaneta(@PathVariable Long id) {
        administradorService.eliminarPlaneta(id);
        return ResponseEntity.noContent().build();
    }

    // === Endpoints para Viajes ===
    @GetMapping("/viajes/traer")
    public ResponseEntity<List<ViajeDTO>> obtenerTodosLosViajes() {
        return ResponseEntity.ok(administradorService.obtenerTodosLosViajes());
    }

    @GetMapping("/viajes/traer/{id}")
    public ResponseEntity<ViajeDTO> obtenerViajePorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerViajePorId(id));
    }

    @PostMapping("/viajes/crear")
    public ResponseEntity<?> agregarNuevoViaje(@RequestBody Viaje viaje) {
        administradorService.agregarNuevoViaje(viaje);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/viajes/actualizar")
    public ResponseEntity<?> actualizarViaje(@RequestBody Viaje viaje) {
        administradorService.actualizarViaje(viaje);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Viaje actualizado correctamente"));
    }

    @DeleteMapping("/viajes/eliminar/{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id) {
        administradorService.eliminarViaje(id);
        return ResponseEntity.noContent().build();
    }

    // === Endpoints para Reservas ===
    @GetMapping("/reservas/traer")
    public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
        return ResponseEntity.ok(administradorService.obtenerTodasLasReservas());
    }

    @GetMapping("/reservas/traer/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerReservaPorId(id));
    }

    @PostMapping("/reservas/crear")
    public ResponseEntity<?> agregarNuevaReserva(@RequestBody ReservaDTO reserva) {
        administradorService.agregarNuevaReserva(reserva);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/reservas/eliminar/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        administradorService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    // === Endpoints para Pasajeros ===
    @GetMapping("/pasajeros/traer")
    public ResponseEntity<List<PasajeroDTO>> obtenerTodosLosPasajeros() {
        return ResponseEntity.ok(administradorService.obtenerTodosLosPasajeros());
    }

    @GetMapping("/pasajeros/traer/{id}")
    public ResponseEntity<PasajeroDTO> obtenerPasajeroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerPasajeroPorId(id));
    }

    @PostMapping("/pasajeros/crear")
    public ResponseEntity<?> agregarNuevoPasajero(@RequestBody PasajeroDTO pasajero) {
        administradorService.agregarNuevoPasajero(pasajero);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/pasajeros/eliminar/{id}")
    public ResponseEntity<?> eliminarPasajero(@PathVariable Long id) {
        administradorService.eliminarPasajero(id);
        return ResponseEntity.noContent().build();
    }
}


