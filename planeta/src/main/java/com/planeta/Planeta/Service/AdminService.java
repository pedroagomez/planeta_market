package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.*;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Repository.IAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService{

    @Autowired
    private IAdminRepository adminRepository;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IPlanetaService planetaService;
    @Autowired
    private IReservaService reservaService;
    @Autowired
    private IViajeService viajeService;
    @Autowired
    private PasajeroService pasajeroService;

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteService.obtenerCliente();
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
         clienteService.actualizarCliente(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteService.eliminarCliente(id);
    }

    // =====================================================
    // Metodos planetas
    @Override
    public List<PlanetaDTO> obtenerTodosLosPlanetas() {
        return planetaService.obtenerPlanetas();
    }

    @Override
    public PlanetaDTO obtenerPlanetaPorId(Long id) {
        return planetaService.obtenerPlanetaPorId(id);
    }

    @Override
    public void agregarNuevoPlaneta(Planeta planeta) {
        planetaService.createPlaneta(planeta);
    }

    @Override
    public void actualizarPlaneta(Planeta planeta) {
       planetaService.actualizarPlaneta(planeta);
    }

    @Override
    public void eliminarPlaneta(Long id) {
        planetaService.eliminarPlaneta(id);
    }


    // =====================================================
    // Metodos viajes

    @Override
    public List<ViajeDTO> obtenerTodosLosViajes() {
        return viajeService.obtenerTodosLosViajes();
    }

    @Override
    public ViajeDTO obtenerViajePorId(Long id) {
        return viajeService.obtenerViajePorId(id);
    }

    @Override
    public void agregarNuevoViaje(Viaje viaje) {
        viajeService.crearViaje(viaje);
    }

    @Override
    public void actualizarViaje(Viaje viaje) {
        viajeService.actualizarViaje(viaje);
    }

    @Override
    public void eliminarViaje(Long id) {
        viajeService.eliminarViaje(id);
    }

    // =====================================================
    // Metodos reservas


    @Override
    public List<ReservaDTO> obtenerTodasLasReservas() {
        return reservaService.obtenerTodasLasReservas();
    }

    @Override
    public ReservaDTO obtenerReservaPorId(Long id) {
        return reservaService.obtenerReservaPorId(id);
    }

    @Override
    public void agregarNuevaReserva(ReservaDTO reserva) {
        reservaService.realizarReserva(reserva);
    }



    @Override
    public void cancelarReserva(Long id) {
            reservaService.cancelarReserva(id);
    }

    // =====================================================
    // Metodos pasajeros

    @Override
    public List<PasajeroDTO> obtenerTodosLosPasajeros() {
        return pasajeroService.ObtenerListaPasajeros();
    }

    @Override
    public PasajeroDTO obtenerPasajeroPorId(Long id) {
        return pasajeroService.obtenerPasajeroPorId(id);
    }

    @Override
    public void agregarNuevoPasajero(PasajeroDTO pasajero) {
        pasajeroService.crearPasajero(pasajero);
    }



    @Override
    public void eliminarPasajero(Long id) {
        pasajeroService.eliminarPasajero(id);
    }


    // =====================================================
    // Metodos adminsitrador

    @Override
    public List<AdministradorDTO> obtenerTodosLosAdministradores() {
        List<Administrador> administradores= adminRepository.findAll();
        return administradores.stream()
                .map(this::mapearAdminADTO)
                .collect(Collectors.toList());
    }


    @Override
    public AdministradorDTO obtenerAdministradorPorId(Long id) {
        Administrador admin= adminRepository.findById(id).orElseThrow(
                ( )-> new RuntimeException("Administrador no encontrado") );
        return mapearAdminADTO(admin);
    }

    @Override
    public void agregarNuevoAdministrador(AdministradorDTO administradorDTO )
    {
        if (adminRepository.buscarPorMail(administradorDTO.getMail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }
        Administrador administrador = mapearDTOAAdmin(administradorDTO);
        adminRepository.save(administrador);
    }



    @Override
    public void eliminarAdministrador(Long id) {
        adminRepository.deleteById(id);
    }


    //=============================
   //MAPEOS
   public AdministradorDTO mapearAdminADTO(Administrador admin)
   {
       AdministradorDTO administradorDTO= new AdministradorDTO();
       administradorDTO.setId(admin.getId());
       administradorDTO.setNombre(admin.getNombre());
       administradorDTO.setApellido(admin.getApellido());
       administradorDTO.setMail(admin.getMail());
       administradorDTO.setPassword(admin.getPassword());
       return administradorDTO;
   }

    public Administrador mapearDTOAAdmin(AdministradorDTO administradorDTO) {
        Administrador administrador = new Administrador();
        administrador.setNombre(administradorDTO.getNombre());
        administrador.setApellido(administradorDTO.getApellido());
        administrador.setMail(administradorDTO.getMail());
        administrador.setPassword(administradorDTO.getPassword());
        return administrador;
    }


    public Administrador verificarCredenciales(String email, String password) {
        Optional<Administrador> admin = adminRepository.buscarPorMail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin.get();
        }
        throw new IllegalArgumentException("Credenciales incorrectas");
    }

}
