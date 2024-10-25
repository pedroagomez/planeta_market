package com.planeta.Planeta.Service;


import com.planeta.Planeta.DTO.*;
import com.planeta.Planeta.Model.*;
import com.planeta.Planeta.Repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        // Busca el cliente en el repositorio
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));

        return cliente;
    }

    // METODOS DE MAPEO

    private List<PropiedadDTO> mapearPropiedadesADTO(List<Propiedad> propiedades) {
        if (propiedades == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si no hay propiedades
        }
        return propiedades.stream()
                .map(this::mapearPropiedadADTO) // Mapea cada propiedad a su DTO
                .collect(Collectors.toList());
    }

    private PropiedadDTO mapearPropiedadADTO(Propiedad propiedad) {
        if (propiedad == null) {
            return null; // Manejo de null
        }
        PropiedadDTO dto = new PropiedadDTO();
        dto.setId(propiedad.getId());
        dto.setClienteId(propiedad.getCliente().getId());
        dto.setPlanetaId(propiedad.getPlaneta().getId());
        dto.setKilometrosCuadrados(propiedad.getKilometrosCuadrados());
        return dto;
    }


    // MAPEO RESERVAS

    private List<ReservaDTO> mapearReservasADTO(List<Reserva> reservas) {
        if (reservas == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si no hay reservas
        }
        return reservas.stream()
                .map(this::mapearReservaADTO) // Mapea cada reserva a su DTO
                .collect(Collectors.toList());
    }

    private ReservaDTO mapearReservaADTO(Reserva reserva) {
        if (reserva == null) {
            return null; // Manejo de null
        }
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setViaje(mapearViajeADTO(reserva.getViaje())); // Asegúrate de implementar este método
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setPasajeros(mapearPasajerosADTO(reserva.getPasajeros())); // Asegúrate de implementar este método
        dto.setPrecioTotal(reserva.getPrecioTotal());
        return dto;
    }

    private ViajeDTO mapearViajeADTO(Viaje viaje) {
        if (viaje == null) {
            return null; // Manejo de null
        }
        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setFechaSalida(viaje.getFechaViaje());
        dto.setDestino(mapearPlanetaADTO(viaje.getDestino())); // Asegúrate de implementar este método
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
            return new ArrayList<>(); // Devuelve una lista vacía si no hay pasajeros
        }
        return pasajeros.stream()
                .map(this::mapearPasajeroADTO) // Mapea cada pasajero a su DTO
                .collect(Collectors.toList());
    }

    private PasajeroDTO mapearPasajeroADTO(Pasajero pasajero) {
        if (pasajero == null) {
            return null; // Manejo de null
        }
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(pasajero.getId());
        dto.setNombre(pasajero.getNombre());
        dto.setApellido(pasajero.getApellido());
        dto.setEmail(pasajero.getEmail());
        dto.setReservaId(pasajero.getReserva().getId());
        return dto;
    }

    @Override
    public List<ClienteDTO> obtenerCliente() {
        List<Cliente> clientes = clienteRepository.findAll(); // Obtén todos los clientes
        return mapearClientesADTO(clientes); // Mapea cada cliente a ClienteDTO
    }

    // Método para mapear una lista de clientes a DTOs
    private List<ClienteDTO> mapearClientesADTO(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::mapearClienteADTO) // Mapea cada Cliente a ClienteDTO
                .collect(Collectors.toList());
    }


    // Método para mapear un cliente a DTO
    private ClienteDTO mapearClienteADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setMail(cliente.getMail());

        // Mapea propiedades sin clienteId y clienteNombre
        List<ClientePlanetaPropiedadDTO> propiedadesDTO = cliente.getPropiedades().stream()
                .map(propiedad -> new ClientePlanetaPropiedadDTO(
                        propiedad.getId(),
                        propiedad.getPlaneta().getId(),
                        propiedad.getPlaneta().getNombre(),
                        propiedad.getKilometrosCuadrados()))
                .collect(Collectors.toList());

        dto.setPropiedades(propiedadesDTO);
        dto.setReservas(mapearReservasADTO(cliente.getReservas())); // Si deseas incluir reservas
        return dto;
    }





    @Override
    public void actualizarCliente(Cliente cliente) {
            clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
