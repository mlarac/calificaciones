package api_notas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api_notas.model.Alumno;
import api_notas.service.AlumnoService;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> findAll() {
        List<Alumno> alumnos = alumnoService.findAll();
        return ResponseEntity.ok(alumnos);
    }

    @PostMapping
    public ResponseEntity<Alumno> save(@RequestBody Alumno alumno) {
        Alumno alumnoguardado = alumnoService.save(alumno);
        return ResponseEntity.ok(alumnoguardado);
    }
}

