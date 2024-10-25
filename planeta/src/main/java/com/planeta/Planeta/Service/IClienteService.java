package com.planeta.Planeta.Service;

import com.planeta.Planeta.DTO.ClienteDTO;
import com.planeta.Planeta.Model.Cliente;

import java.util.List;

public interface IClienteService {

    void createCliente(Cliente cliente);

    Cliente obtenerClientePorId(Long id);
    List<ClienteDTO> obtenerCliente();

    void actualizarCliente(Cliente cliente);

    void eliminarCliente(Long id);


}
