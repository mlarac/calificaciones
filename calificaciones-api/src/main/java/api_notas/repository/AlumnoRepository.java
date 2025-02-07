package api_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_notas.model.Alumno;



@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}