package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entidad que representa a los profesores del sistema académico
 * Extiende de PersonaEntity usando herencia JOINED
 * 
 * @author HACHO HAHO JHABEL
 */
@Entity
@Table(name = "profesores")
@DiscriminatorValue("Profesor")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class ProfesorEntity extends PersonaEntity {
    
    @Column(name = "codigo_empleado", nullable = false, unique = true, length = 20)
    private String codigoProfesor;
    
    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;
    
    // Relación con los cursos que imparte el profesor
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.entity.CursoEntity> cursos;
}
