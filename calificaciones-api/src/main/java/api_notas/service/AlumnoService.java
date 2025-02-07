package api_notas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_notas.model.Alumno;
import api_notas.repository.AlumnoRepository;

import java.util.List;


@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public List<Alumno> findAll() {
        return alumnoRepository.findAll();
    }
    
    
}
