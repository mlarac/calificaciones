package notas_front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {
    private Long id;
    private String nombre;
    private AlumnoDTO alumno;
}
