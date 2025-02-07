package api_notas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api_notas.model.Materia;


public interface MateriaRepository extends JpaRepository<Materia, Long> {

    public Optional<Materia> findById(long id);

}
