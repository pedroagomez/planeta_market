package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.PlanetaDTO;
import com.planeta.Planeta.Model.Planeta;
import com.planeta.Planeta.Service.IPlanetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/planetas")
public class PlanetaController {

    @Autowired
    IPlanetaService planetaService;


    @GetMapping("traer")
    public ResponseEntity<List<PlanetaDTO>> obtenerClientes() {
        List<PlanetaDTO> planeta = planetaService.obtenerPlanetas();
        return ResponseEntity.ok(planeta);
    }

    @GetMapping("traer/{id}")
    public ResponseEntity<?> obtenerPlanetaPorId(@PathVariable Long id) {
        try {
            PlanetaDTO planeta = planetaService.obtenerPlanetaPorId(id);
            return ResponseEntity.ok(planeta);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("mensaje","Planeta no encontrado"));
        }

    }


    @PostMapping("crear")
    public ResponseEntity<?> crearPlaneta(@Valid @RequestBody Planeta planeta)
    {
        try{
            planetaService.createPlaneta(planeta);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Collections.singletonMap("mensaje","Error al crear pasajero "+ e.getMessage()));
        }

    }
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminarPlaneta(@PathVariable Long id) {
        planetaService.eliminarPlaneta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlaneta(@PathVariable Long id, @Valid @RequestBody Planeta planeta) {

        try{
            planetaService.actualizarPlaneta(planeta);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(Collections.singletonMap("mensaje","Error al actualizar pasajero "+ e.getMessage()));
        }
    }
}

