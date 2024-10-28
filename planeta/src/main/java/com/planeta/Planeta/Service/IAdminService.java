package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.*;
import com.planeta.Planeta.Model.*;

import java.util.List;

public interface IAdminService {


        // Métodos para gestionar clientes
        List<ClienteDTO> obtenerTodosLosClientes();
        Cliente obtenerClientePorId(Long id);
        void actualizarCliente(Cliente cliente);
        void eliminarCliente(Long id);

        // Métodos para gestionar planetas
        List<PlanetaDTO> obtenerTodosLosPlanetas();
        PlanetaDTO obtenerPlanetaPorId(Long id);
        void agregarNuevoPlaneta(Planeta planeta);
        void actualizarPlaneta(Planeta planeta);
        void eliminarPlaneta(Long id);

        // Métodos para gestionar viajes
        List<ViajeDTO> obtenerTodosLosViajes();
        ViajeDTO obtenerViajePorId(Long id);
        void agregarNuevoViaje(Viaje viaje);
        void actualizarViaje(Viaje viaje);
        void eliminarViaje(Long id);

        // Métodos para gestionar reservas
        List<ReservaDTO> obtenerTodasLasReservas();
        ReservaDTO obtenerReservaPorId(Long id);
        void agregarNuevaReserva(ReservaDTO reserva);
        void cancelarReserva(Long id);

        // Métodos para gestionar pasajeros
        List<PasajeroDTO> obtenerTodosLosPasajeros();
        PasajeroDTO obtenerPasajeroPorId(Long id);
        void agregarNuevoPasajero(PasajeroDTO pasajero);
        void eliminarPasajero(Long id);

        // Métodos para gestionar administradores
        List<AdministradorDTO> obtenerTodosLosAdministradores();
        AdministradorDTO obtenerAdministradorPorId(Long id);
        void agregarNuevoAdministrador(AdministradorDTO administrador);
        void eliminarAdministrador(Long id);

}
