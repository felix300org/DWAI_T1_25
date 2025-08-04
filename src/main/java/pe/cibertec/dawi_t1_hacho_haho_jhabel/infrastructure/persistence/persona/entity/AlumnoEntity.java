package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entidad hija que representa a los alumnos del sistema académico
 * Extiende de PersonaEntity usando herencia JOINED
 * RESPUESTA A PREGUNTA 01 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Entity
@Table(name = "alumnos")
@DiscriminatorValue("Alumno")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class AlumnoEntity extends PersonaEntity {
    
    @Column(name = "codigo_estudiante", nullable = false, unique = true, length = 20)
    private String codigoEstudiante;
    
    @Column(name = "semestre_ingreso", nullable = false)
    private Integer semestreIngreso;
    
    // Relación con matrículas
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.entity.MatriculaEntity> matriculas;
    

}
