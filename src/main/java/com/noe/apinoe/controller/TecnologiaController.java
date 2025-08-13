package com.noe.apinoe.controller;

import com.noe.apinoe.model.Tecnologia;
import com.noe.apinoe.service.TecnologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tecnologias")
@CrossOrigin(origins = "*")
public class TecnologiaController {

    @Autowired
    private TecnologiaService tecnologiaService;

    @GetMapping
    public ResponseEntity<List<Tecnologia>> getAllTecnologias() {
        List<Tecnologia> tecnologias = tecnologiaService.findAll();
        return ResponseEntity.ok(tecnologias);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Tecnologia>> getTecnologiasActivas() {
        List<Tecnologia> tecnologias = tecnologiaService.findActivos();
        return ResponseEntity.ok(tecnologias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnologia> getTecnologiaById(@PathVariable Integer id) {
        Optional<Tecnologia> tecnologia = tecnologiaService.findById(id);
        return tecnologia.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Tecnologia>> buscarTecnologias(@RequestParam String nombre) {
        List<Tecnologia> tecnologias = tecnologiaService.findByNombreContaining(nombre);
        return ResponseEntity.ok(tecnologias);
    }

    @PostMapping
    public ResponseEntity<Tecnologia> createTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
        Tecnologia nuevaTecnologia = tecnologiaService.save(tecnologia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTecnologia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnologia> updateTecnologia(@PathVariable Integer id,
                                                       @Valid @RequestBody Tecnologia tecnologia) {
        try {
            Tecnologia tecnologiaActualizada = tecnologiaService.update(id, tecnologia);
            return ResponseEntity.ok(tecnologiaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
