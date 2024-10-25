package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.ClientePlanetaPropiedadDTO;
import com.planeta.Planeta.Model.Cliente;
import com.planeta.Planeta.Model.ClientePlanetaPropiedad;
import com.planeta.Planeta.Model.Planeta;
import com.planeta.Planeta.Repository.IClientePlanetaRepo;
import com.planeta.Planeta.Repository.IClienteRepository;
import com.planeta.Planeta.Repository.IPlanetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientePlanetaService implements IClientePlanetaService {

    @Autowired
    private IClientePlanetaRepo clientePlanetaRepo;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IPlanetaRepository planetaRepository;
    @Override
    public void crearClientePlaneta(ClientePlanetaPropiedad clientePlanetaPropiedad) {
        Cliente cliente = clienteRepository.findById(clientePlanetaPropiedad.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Planeta planeta = planetaRepository.findById(clientePlanetaPropiedad.getPlaneta().getId())
                .orElseThrow(() -> new RuntimeException("Planeta no encontrado"));

        // Verifico que los KM no sean null
        Double kmCuadrados = clientePlanetaPropiedad.getKilometrosCuadrados();
        if (kmCuadrados == null) {
            throw new RuntimeException("El valor de kilómetros cuadrados no puede ser nulo");
        }

        // Verifico que haya kms disponibles
        if (planeta.getKmCuadrados() < kmCuadrados) {
            throw new RuntimeException("No hay suficientes kilómetros cuadrados disponibles en el planeta");
        }
        planeta.setKmCuadrados((int) (planeta.getKmCuadrados() - kmCuadrados));

        // Asigno el cliente y planeta a la propiedad
        clientePlanetaPropiedad.setCliente(cliente);
        clientePlanetaPropiedad.setPlaneta(planeta);

        clientePlanetaRepo.save(clientePlanetaPropiedad);
        planetaRepository.save(planeta);
    }




    @Override
    public ClientePlanetaPropiedadDTO obtenerClientePlanetaPorId(Long id) {
        ClientePlanetaPropiedad propiedad = clientePlanetaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        // Mapear a DTO
        return new ClientePlanetaPropiedadDTO(
                propiedad.getId(),
                propiedad.getCliente().getId(),
                propiedad.getCliente().getNombre(),
                propiedad.getPlaneta().getId(),
                propiedad.getPlaneta().getNombre(),
                propiedad.getKilometrosCuadrados());
    }

    @Override
    public List<ClientePlanetaPropiedadDTO> obtenerTodosClientesPlanetas() {
        List<ClientePlanetaPropiedad> propiedades = clientePlanetaRepo.findAll();

        return propiedades.stream().map(propiedad -> new ClientePlanetaPropiedadDTO(
                propiedad.getId(),
                propiedad.getCliente().getId(),
                propiedad.getCliente().getNombre(),
                propiedad.getPlaneta().getId(),
                propiedad.getPlaneta().getNombre(),
                propiedad.getKilometrosCuadrados()
        )).collect(Collectors.toList());
    }

    @Override
    public void actualizarClientePlaneta(Long id, ClientePlanetaPropiedad clientePlanetaPropiedad) {
        ClientePlanetaPropiedad propiedadExistente = clientePlanetaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        propiedadExistente.setKilometrosCuadrados(clientePlanetaPropiedad.getKilometrosCuadrados());
        clientePlanetaRepo.save(propiedadExistente);
    }

    @Override
    public void eliminarClientePlaneta(Long id) {
        clientePlanetaRepo.deleteById(id);
    }
}
