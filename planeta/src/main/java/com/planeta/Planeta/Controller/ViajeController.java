package com.planeta.Planeta.Controller;

import com.planeta.Planeta.DTO.ViajeDTO;
import com.planeta.Planeta.Model.Viaje;
import com.planeta.Planeta.Service.IViajeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/viajes")
public class ViajeController {

    @Autowired
    private IViajeService viajeService;

    @GetMapping("/traer")
    public ResponseEntity<List<ViajeDTO>>obtenerViaje()
    {
        List<ViajeDTO>listaViaje=viajeService.obtenerTodosLosViajes();
        return ResponseEntity.ok(listaViaje);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<Viaje> obtenerViajePorId(@PathVariable Long id) {
        try {
            Viaje viaje = viajeService.obtenerViajePorId(id);
            return ResponseEntity.ok(viaje);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearViaje(@Valid @RequestBody Viaje viaje)
    {
        try
        {
            viajeService.crearViaje(viaje);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "Error al crear viaje: " + e.getMessage()));
        }

    }



    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id)
    {
        viajeService.eliminarViaje(id);
        return ResponseEntity.ok().build();
    }
}
