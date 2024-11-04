package com.ejemplo.incidencias.controller;

import com.ejemplo.incidencias.dto.IncidenciaDTO;
import com.ejemplo.incidencias.model.Incidencia;
import com.ejemplo.incidencias.repository.IncidenciaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    // POST - Crear incidencia
    @PostMapping
    public ResponseEntity<?> crearIncidencia(
        @RequestHeader("correo-docente") String correoDocente,
        @RequestBody IncidenciaDTO incidenciaDTO) {

        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia.setEstado(incidenciaDTO.getEstado());
        incidencia.setTipo(incidenciaDTO.getTipo());
        incidenciaRepository.save(incidencia);

        return ResponseEntity.status(HttpStatus.CREATED).body("Incidencia creada con éxito");
    }

    // DELETE - Borrar incidencia
    @DeleteMapping
    public ResponseEntity<?> borrarIncidencia(@RequestBody IncidenciaDTO incidenciaDTO) {
        Optional<Incidencia> incidencia = incidenciaRepository.findById(incidenciaDTO.getId());

        if (incidencia.isPresent()) {
            incidenciaRepository.delete(incidencia.get());
            return ResponseEntity.ok("Incidencia borrada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incidencia no encontrada");
        }
    }

    // PUT - Modificar incidencia existente
    @PutMapping
    public ResponseEntity<?> modificarIncidencia(@RequestBody IncidenciaDTO incidenciaDTO) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(incidenciaDTO.getId());

        if (incidenciaOpt.isPresent()) {
            Incidencia incidencia = incidenciaOpt.get();
            incidencia.setDescripcion(incidenciaDTO.getDescripcion());
            incidencia.setEstado(incidenciaDTO.getEstado());
            incidencia.setTipo(incidenciaDTO.getTipo());

            incidenciaRepository.saveAndFlush(incidencia);
            return ResponseEntity.ok("Incidencia modificada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incidencia no encontrada");
        }
    }

    // GET - Buscar incidencias con filtro dinámico
    @GetMapping
    public ResponseEntity<List<Incidencia>> buscarIncidencias(@RequestBody IncidenciaDTO incidenciaDTO) {
        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia.setEstado(incidenciaDTO.getEstado());
        incidencia.setTipo(incidenciaDTO.getTipo());

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreNullValues();

        Example<Incidencia> example = Example.of(incidencia, matcher);
        List<Incidencia> incidencias = incidenciaRepository.findAll(example);

        return ResponseEntity.ok(incidencias);
    }
}