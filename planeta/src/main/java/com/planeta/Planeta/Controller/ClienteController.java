package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.ClienteDTO;
import com.planeta.Planeta.Model.Cliente;

import com.planeta.Planeta.Service.IClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/traer")
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        try {
            List<ClienteDTO> clientes = clienteService.obtenerCliente();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((List<ClienteDTO>) Collections.singletonMap("mensaje", "Error al obtener clientes: " + e.getMessage()));
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerClientePorId( @PathVariable Long id) {
        try {
            Cliente cliente = clienteService.obtenerClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Cliente no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al obtener cliente: " + e.getMessage()));
        }
    }

    @PostMapping("crear")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente) {
        try {
            clienteService.createCliente(cliente);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear cliente: " + e.getMessage()));
        }
    }



    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Cliente no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al eliminar cliente: " + e.getMessage()));
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            // Verifica si el cliente existe
            Cliente clienteExistenteDTO = clienteService.obtenerClientePorId(id);
            if (clienteExistenteDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("mensaje", "Cliente no encontrado con ID: " + id));
            }

            // Crea un nuevo Cliente a partir del DTO
            Cliente clienteExistente = new Cliente();
            clienteExistente.setId(id);
            clienteExistente.setNombre(clienteDTO.getNombre());
            clienteExistente.setApellido(clienteDTO.getApellido());
            clienteExistente.setMail(clienteDTO.getMail());

            clienteService.actualizarCliente(clienteExistente);
            return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al actualizar cliente: " + e.getMessage()));
        }
    }
}
