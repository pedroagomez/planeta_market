package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.Model.Pasajero;
import com.planeta.Planeta.Repository.IPasajeroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasajeroService implements IPasajeroService {

    @Autowired
    private IPasajeroRepository pasajeroRepository;

    @Override
    public void crearPasajero(PasajeroDTO pasajeroDTO) {
        Pasajero pasajero = new Pasajero();
        pasajero.setId(pasajeroDTO.getId());
        pasajero.setNombre(pasajeroDTO.getNombre());
        pasajero.setApellido(pasajeroDTO.getApellido());
        pasajero.setEmail(pasajeroDTO.getEmail());
        pasajeroRepository.save(pasajero);
    }


    @Override
    public PasajeroDTO obtenerPasajeroPorId(Long id) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pasajero no encontrado con id: " + id));
        return convertirADTO(pasajero);
    }

    @Override
    public List<PasajeroDTO> ObtenerListaPasajeros() {
        List<Pasajero> pasajeros = pasajeroRepository.findAll();
        return pasajeros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void actualizarPasajero(Long id, PasajeroDTO pasajeroDTO) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pasajero no encontrado con id: " + id));

        pasajero.setNombre(pasajeroDTO.getNombre());
        pasajero.setApellido(pasajeroDTO.getApellido());
        pasajero.setEmail(pasajeroDTO.getEmail());
        // No actualizamos la reserva aquí, eso debería manejarse en un servicio de Reserva

        pasajeroRepository.save(pasajero);
    }

    @Override
    public void eliminarPasajero(Long id) {
        if (!pasajeroRepository.existsById(id)) {
            throw new EntityNotFoundException("Pasajero no encontrado con id: " + id);
        }
        pasajeroRepository.deleteById(id);
    }

    private PasajeroDTO convertirADTO(Pasajero pasajero) {
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(pasajero.getId());
        dto.setNombre(pasajero.getNombre());
        dto.setApellido(pasajero.getApellido());
        dto.setEmail(pasajero.getEmail());
        dto.setReservaId(pasajero.getReserva() != null ? pasajero.getReserva().getId() : null);
        return dto;
    }

    private Pasajero convertirAEntidad(PasajeroDTO dto) {
        Pasajero pasajero = new Pasajero();
        pasajero.setId(dto.getId());
        pasajero.setNombre(dto.getNombre());
        pasajero.setApellido(dto.getApellido());
        pasajero.setEmail(dto.getEmail());
        // La reserva se establece en otro lugar, no aquí
        return pasajero;
    }


}
