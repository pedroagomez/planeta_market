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
    private static Administrador loggedInAdmin = null;

    // === Endpoints para Administradores ===
    @PostMapping("/crear")
    public ResponseEntity<?> crearAdmin(@Valid @RequestBody AdministradorDTO administrador) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }

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
            loggedInAdmin=admin;
            return ResponseEntity.ok(admin);
        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();
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
                if (admin.isLoggedIn()) {

                    admin.setLoggedIn(false);
                    administradorService.guardar(admin);
                    loggedInAdmin=null;

                    return ResponseEntity.ok(Collections.singletonMap("mensaje", "Administrador desconectado: " + admin.getMail()));
                } else {

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
    public ResponseEntity<?> obtenerAdmin() {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }

        try {
            List<AdministradorDTO> admin = administradorService.obtenerTodosLosAdministradores();
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener admin: " + e.getMessage()));
        }
    }


    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerAdminId(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
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
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
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
    // === Endpoints para Clientes ===
    @GetMapping("/clientes/traer")
    public ResponseEntity<?> obtenerTodosLosClientes() {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            List<ClienteDTO> clientes = administradorService.obtenerTodosLosClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener clientes: " + e.getMessage()));
        }
    }

    @GetMapping("/clientes/traer/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            Cliente cliente = administradorService.obtenerClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener el cliente: " + e.getMessage()));
        }
    }

    @PutMapping("/clientes/actualizar")
    public ResponseEntity<?> actualizarCliente(@RequestBody Cliente cliente) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.actualizarCliente(cliente);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Cliente actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al actualizar el cliente: " + e.getMessage()));
        }
    }

    @DeleteMapping("/clientes/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar el cliente: " + e.getMessage()));
        }
    }

    // === Endpoints para Planetas ===
    @GetMapping("/planetas/traer")
    public ResponseEntity<?> obtenerTodosLosPlanetas() {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            List<PlanetaDTO> planetas = administradorService.obtenerTodosLosPlanetas();
            return ResponseEntity.ok(planetas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener planetas: " + e.getMessage()));
        }
    }

    @GetMapping("/planetas/traer/{id}")
    public ResponseEntity<?> obtenerPlanetaPorId(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            PlanetaDTO planeta = administradorService.obtenerPlanetaPorId(id);
            return ResponseEntity.ok(planeta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener el planeta: " + e.getMessage()));
        }
    }

    @PostMapping("/planetas/crear")
    public ResponseEntity<?> agregarNuevoPlaneta(@RequestBody Planeta planeta) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.agregarNuevoPlaneta(planeta);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear el planeta: " + e.getMessage()));
        }
    }

    @PutMapping("/planetas/actualizar")
    public ResponseEntity<?> actualizarPlaneta(@RequestBody Planeta planeta) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.actualizarPlaneta(planeta);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Planeta actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al actualizar el planeta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/planetas/eliminar/{id}")
    public ResponseEntity<?> eliminarPlaneta(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.eliminarPlaneta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar el planeta: " + e.getMessage()));
        }
    }

    // === Endpoints para Viajes ===
    @GetMapping("/viajes/traer")
    public ResponseEntity<?> obtenerTodosLosViajes() {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            List<ViajeDTO> viajes = administradorService.obtenerTodosLosViajes();
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener viajes: " + e.getMessage()));
        }
    }

    @GetMapping("/viajes/traer/{id}")
    public ResponseEntity<?> obtenerViajePorId(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            ViajeDTO viaje = administradorService.obtenerViajePorId(id);
            return ResponseEntity.ok(viaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener el viaje: " + e.getMessage()));
        }
    }

    @PostMapping("/viajes/crear")
    public ResponseEntity<?> agregarNuevoViaje(@RequestBody Viaje viaje) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.agregarNuevoViaje(viaje);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear el viaje: " + e.getMessage()));
        }
    }

    @PutMapping("/viajes/actualizar")
    public ResponseEntity<?> actualizarViaje(@RequestBody Viaje viaje) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.actualizarViaje(viaje);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Viaje actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al actualizar el viaje: " + e.getMessage()));
        }
    }

    @DeleteMapping("/viajes/eliminar/{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.eliminarViaje(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar el viaje: " + e.getMessage()));
        }
    }

    // === Endpoints para Reservas ===
    @GetMapping("/reservas/traer")
    public ResponseEntity<?> obtenerTodasLasReservas() {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            List<ReservaDTO> reservas = administradorService.obtenerTodasLasReservas();
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener reservas: " + e.getMessage()));
        }
    }

    @GetMapping("/reservas/traer/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            ReservaDTO reserva = administradorService.obtenerReservaPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener la reserva: " + e.getMessage()));
        }
    }

    @PostMapping("/reservas/crear")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reserva) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.agregarNuevaReserva(reserva);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear la reserva: " + e.getMessage()));
        }
    }



    @DeleteMapping("/reservas/eliminar/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "No autorizado. Inicie sesión."));
        }
        try {
            administradorService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar la reserva: " + e.getMessage()));
        }
    }
}


