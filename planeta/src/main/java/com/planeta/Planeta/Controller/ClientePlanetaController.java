package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.ClientePlanetaPropiedadDTO;
import com.planeta.Planeta.Model.ClientePlanetaPropiedad;
import com.planeta.Planeta.Service.IClientePlanetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes-planetas")
public class ClientePlanetaController {

    @Autowired
    private IClientePlanetaService clientePlanetaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearClientePlaneta(@Valid @RequestBody ClientePlanetaPropiedad clientePlanetaPropiedad) {

        try
        {
            clientePlanetaService.crearClientePlaneta(clientePlanetaPropiedad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear cliente-planeta: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientePlanetaPropiedadDTO> obtenerClientePlanetaPorId(@PathVariable Long id) {
        ClientePlanetaPropiedadDTO propiedad = clientePlanetaService.obtenerClientePlanetaPorId(id);
        return ResponseEntity.ok(propiedad);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<ClientePlanetaPropiedadDTO>> obtenerTodosClientesPlanetas() {
        List<ClientePlanetaPropiedadDTO> propiedades = clientePlanetaService.obtenerTodosClientesPlanetas();
        return ResponseEntity.ok(propiedades);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizarClientePlaneta(@PathVariable Long id, @RequestBody ClientePlanetaPropiedad clientePlanetaPropiedad) {
        clientePlanetaService.actualizarClientePlaneta(id, clientePlanetaPropiedad);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarClientePlaneta(@PathVariable Long id) {
        clientePlanetaService.eliminarClientePlaneta(id);
        return ResponseEntity.noContent().build();
    }
}
