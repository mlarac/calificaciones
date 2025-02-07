package api_notas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_notas.model.Materia;
import api_notas.repository.MateriaRepository;
@Service
public class MateriaService {
	@Autowired
    private MateriaRepository materiaRepository;
	
	public Materia save(Materia materia) {
        return materiaRepository.save(materia);
    }
	
}
