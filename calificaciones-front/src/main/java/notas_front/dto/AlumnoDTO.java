package notas_front.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private Set<MateriaDTO> materiaList;
}
