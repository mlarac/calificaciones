package api_notas.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Data
public class Alumno {
    private static final Logger logger = LoggerFactory.getLogger(Alumno.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private Set<Materia> materiaLista;

    // Constructor
    public Alumno() {
        logger.info("Creando una nueva instancia de Alumno");
    }

    // Métodos setter con logging
    public void setRut(String rut) {
        logger.debug("Estableciendo RUT: {}", rut);
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        logger.debug("Estableciendo nombre: {}", nombre);
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        logger.debug("Estableciendo dirección: {}", direccion);
        this.direccion = direccion;
    }

    public void setMateriaLista(Set<Materia> materiaLista) {
        logger.info("Estableciendo lista de materias. Tamaño: {}", materiaLista.size());
        this.materiaLista = materiaLista;
    }
}
