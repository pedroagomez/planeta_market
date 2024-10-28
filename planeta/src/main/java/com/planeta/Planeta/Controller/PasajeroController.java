package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.PasajeroDTO;
import com.planeta.Planeta.Model.Pasajero;
import com.planeta.Planeta.Service.IPasajeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pasajeros")
public class PasajeroController {

    @Autowired
    private IPasajeroService pasajeroService;

    @GetMapping("/traer")
    public ResponseEntity<List<PasajeroDTO>>obtenerListaPasajeros()
    {
        List<PasajeroDTO> pasajeros = pasajeroService.ObtenerListaPasajeros();
        return ResponseEntity.ok(pasajeros);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerPasajeroPorId(@PathVariable Long id)
    {
        try {
            PasajeroDTO pasajero = pasajeroService.obtenerPasajeroPorId(id);
            return ResponseEntity.ok(pasajero);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(Collections.singletonMap("mensaje", "Error al obtener pasajero: " + e.getMessage()));
        }

    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPasajero(@Valid @RequestBody PasajeroDTO pasajero)
    {
        try{

            pasajeroService.crearPasajero(pasajero);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(Collections.singletonMap("mensaje", "Error al crear pasajero: " + e.getMessage()));
        }

    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPasajero(@PathVariable Long id,
                                                         @Valid @RequestBody PasajeroDTO pasajero)
    {
        try
        {
            pasajeroService.actualizarPasajero(id,pasajero);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(Collections.singletonMap("mensaje", "Error al actualizar pasajero: " + e.getMessage()));
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPasajero(@PathVariable Long id)
    {
        pasajeroService.eliminarPasajero(id);
        return ResponseEntity.noContent().build();
    }
}
