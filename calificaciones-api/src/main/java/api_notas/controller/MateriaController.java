package api_notas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_notas.model.Materia;
import api_notas.service.MateriaService;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
	@Autowired
	private MateriaService materiaService;
	
	public ResponseEntity<Materia> save(@RequestBody Materia materia){
		Materia materiaguardada= materiaService.save(materia);
		return ResponseEntity.ok(materiaguardada);
		
	}
	

}
