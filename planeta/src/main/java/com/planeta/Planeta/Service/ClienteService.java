package com.planeta.Planeta.Service;


import com.planeta.Planeta.DTO.*;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public void createCliente(Cliente cliente) {
        if (clienteRepository.existsByMail(cliente.getMail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        clienteRepository.save(cliente);
    }


    @Override
    public Cliente obtenerClientePorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));

        return cliente;
    }


    @Override
    public List<ClienteDTO> obtenerCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        return mapearClientesADTO(clientes);
    }


    @Override
    public void actualizarCliente(Cliente cliente) {
            clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    //===============================
    //MAPEOS
    private ClienteDTO mapearClienteADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setMail(cliente.getMail());
        dto.setPassword(cliente.getPassword());


        List<ClientePlanetaPropiedadDTO> propiedadesDTO = cliente.getPropiedades().stream()
                .map(propiedad -> new ClientePlanetaPropiedadDTO(
                        propiedad.getId(),
                        propiedad.getPlaneta().getId(),
                        propiedad.getPlaneta().getNombre(),
                        propiedad.getKilometrosCuadrados()))
                .collect(Collectors.toList());

        dto.setPropiedades(propiedadesDTO);
        dto.setReservas(mapearReservasADTO(cliente.getReservas()));
        return dto;
    }
    private List<ClienteDTO> mapearClientesADTO(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::mapearClienteADTO)
                .collect(Collectors.toList());
    }

    private PasajeroDTO mapearPasajeroADTO(Pasajero pasajero) {
        if (pasajero == null) {
            return null;
        }
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(pasajero.getId());
        dto.setNombre(pasajero.getNombre());
        dto.setApellido(pasajero.getApellido());
        dto.setEmail(pasajero.getEmail());
        dto.setReservaId(pasajero.getReserva().getId());
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
            return null; // Manejo de null
        }
        PlanetaDTO dto = new PlanetaDTO();
        dto.setId(planeta.getId());
        dto.setNombre(planeta.getNombre());
        dto.setTipo(planeta.getTipo());
        dto.setKmCuadrados(planeta.getKmCuadrados());
        return dto;
    }



    private List<PasajeroDTO> mapearPasajerosADTO(List<Pasajero> pasajeros) {
        if (pasajeros == null) {
            return new ArrayList<>();
        }
        return pasajeros.stream()
                .map(this::mapearPasajeroADTO)
                .collect(Collectors.toList());
    }

    private List<ReservaDTO> mapearReservasADTO(List<Reserva> reservas) {
        if (reservas == null) {
            return new ArrayList<>();
        }
        return reservas.stream()
                .map(this::mapearReservaADTO)
                .collect(Collectors.toList());
    }

    private ReservaDTO mapearReservaADTO(Reserva reserva) {
        if (reserva == null) {
            return null; // Manejo de null
        }
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setViaje(mapearViajeADTO(reserva.getViaje()));
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setPasajeros(mapearPasajerosADTO(reserva.getPasajeros()));
        dto.setPrecioTotal(reserva.getPrecioTotal());
        return dto;
    }


    // METODOS DE MAPEO DE PROPIEDADES

    private List<PropiedadDTO> mapearPropiedadesADTO(List<Propiedad> propiedades) {
        if (propiedades == null) {
            return new ArrayList<>();
        }
        return propiedades.stream()
                .map(this::mapearPropiedadADTO)
                .collect(Collectors.toList());
    }

    private PropiedadDTO mapearPropiedadADTO(Propiedad propiedad) {
        if (propiedad == null) {
            return null;
        }
        PropiedadDTO dto = new PropiedadDTO();
        dto.setId(propiedad.getId());
        dto.setClienteId(propiedad.getCliente().getId());
        dto.setPlanetaId(propiedad.getPlaneta().getId());
        dto.setKilometrosCuadrados(propiedad.getKilometrosCuadrados());
        return dto;
    }


    public Cliente verificarCredenciales(String email, String password) {
        Optional<Cliente> cliente = clienteRepository.buscarPorMail(email);
        if (cliente.isPresent() && cliente.get().getPassword().equals(password)) {
            return cliente.get();
        }
        throw new IllegalArgumentException("Credenciales incorrectas");
    }

}
