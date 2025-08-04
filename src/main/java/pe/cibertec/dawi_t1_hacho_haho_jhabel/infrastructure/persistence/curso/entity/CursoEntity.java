package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.entity;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.shared.Auditoria;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entidad que representa los cursos del sistema académico
 * Incluye campos de auditoría automática
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Entity
@Table(name = "cursos")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class CursoEntity extends Auditoria<String> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    
    @Column(name = "creditos", nullable = false)
    private Integer creditos;
    
    @Column(name = "codigo_curso", nullable = false, unique = true, length = 10)
    private String codigoCurso;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    // Relación con matrículas
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.entity.MatriculaEntity> matriculas;
    

}
